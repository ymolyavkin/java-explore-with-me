package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.Client;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventMapper;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.mapper.Mapper;
import ru.practicum.ewm.dto.request.*;
import ru.practicum.ewm.entity.*;
import ru.practicum.ewm.enums.EventsState;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationDateException;
import ru.practicum.ewm.repository.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest.Status.CONFIRMED;
import static ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest.Status.REJECTED;
import static ru.practicum.ewm.dto.request.UpdateEventAdminRequest.StateAction.PUBLISH_EVENT;
import static ru.practicum.ewm.dto.request.UpdateEventUserRequest.StateAction.SEND_TO_REVIEW;
import static ru.practicum.ewm.enums.EventsState.PUBLISHED;
import static ru.practicum.util.Constants.MESSAGE_DATE_NOT_VALID;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_START_AFTER_END;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper mapper;
    private final Client statClient;

    @Override
    public List<EventShortDto> getEventsByOwner(Long userId, int from, int size) {
        log.info("Private: Получение событий, добавленных текущим пользователем с id = {}", userId);
        Page<Event> pageEvent = eventRepository.findAllByInitiator_Id(userId, PageRequest.of(from, size));
        List<Event> events = pageEvent.getContent();
        List<EventShortDto> eventsShort = events.stream().map(event -> mapper.map(event, EventShortDto.class)).collect(Collectors.toList());
        List<EventsConfirmedRequest> confirmedRequests = requestRepository.getCountConfirmedRequests();
        Map<Long, Long> mapConfirmedRequests = confirmedRequests
                .stream()
                .collect(Collectors.toMap(request -> request.getEventId(), request -> request.getCountConfirmedRequests()));
        eventsShort.forEach((eventFullDto -> {
            if (mapConfirmedRequests.containsKey(eventFullDto.getId())) {
                eventFullDto.setConfirmedRequests(mapConfirmedRequests.get(eventFullDto.getId()));
            } else {
                eventFullDto.setConfirmedRequests(0L);
            }
        }));
        Map<Long, Long> eventViews = getViews(events);
        eventsShort.forEach(e -> e.setViews(eventViews.get(e.getId())));

        return eventsShort;
    }

    @Override
    public EventFullDto getEventFullAddedCurrentUser(Long userId, Long eventId) {
        log.info("Private: Получение полной информации о событии с id = {}, добавленном текущим пользователем с id = {}", eventId, userId);
        Event event = eventRepository.findByInitiator_IdAndAndId(userId, eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s, добавленное пользователем с id %s не найдено", eventId, userId)));
        EventFullDto eventFullDto = mapper.map(event, EventFullDto.class);
        eventFullDto.setConfirmedRequests(requestRepository.findConfirmedRequests(eventFullDto.getId()));
        List<Event> events = List.of(event);
        Map<Long, Long> eventViews = getViews(events);
        eventFullDto.setViews(eventViews.get(eventFullDto.getId()));

        return eventFullDto;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        log.info("Private: Добавление нового события");
        if (newEventDto.getEventDate() != null && newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationDateException(MESSAGE_DATE_NOT_VALID);
        }
        User initiator = getUserById(userId);
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new NotFoundException(String.format("Категория  %s не найдена", newEventDto.getCategory())));

        Location savedLocation = locationRepository.save(mapper.map(newEventDto.getLocation(), Location.class));
        Event savedEvent = eventRepository.save(EventMapper.mapToEvent(newEventDto, category, savedLocation, initiator));

        return mapper.map(savedEvent, EventFullDto.class);
    }

    @Override
    public EventFullDto editEventFromAuthor(Long userId, Long eventId, UpdateEventUserRequest eventToUpdate) {
        log.info("Private: Изменение события с id {}, добавленного пользователем с id {}", eventId, userId);

        Event event = getEventById(eventId);
        if (event.getEventsState().equals(PUBLISHED)) {
            throw new NotAvailableException("Изменить можно только отмененные события или события в состоянии ожидания модерации");
        }
        if (eventToUpdate.getCategory() != null) {
            event.setCategory(categoryRepository.findById(eventToUpdate.getCategory()).orElseThrow(() ->
                    new NotFoundException("Категории с id = " + eventToUpdate.getCategory() + " не существует")));
        }
        if (eventToUpdate.getEventDate() != null && eventToUpdate.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationDateException(MESSAGE_DATE_NOT_VALID);
        }
        getUserById(userId);
        patchUpdateEvent(eventToUpdate, event);

        if (eventToUpdate.getStateAction() != null) {
            if (eventToUpdate.getStateAction().equals(SEND_TO_REVIEW)) {
                event.setEventsState(EventsState.PENDING);
            } else {
                event.setEventsState(EventsState.CANCELED);
            }
        }
        return mapper.map(eventRepository.save(event), EventFullDto.class);
    }

    @Override
    public List<ParticipationRequestDto> getRequestToParticipationByUser(Long userId, Long eventId) {
        log.info("Private: Получение запросов на участие в событии с id {} пользователя с id {}", eventId, userId);
        getUserById(userId);
        getEventById(eventId);

        return requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId).stream()
                .map(request -> Mapper.mapToParticipationRequestDto(mapper, request)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult changeStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest requestToStatusUpdate) {
        log.info("Private: Изменение статуса заявок на участие в событии с id {} пользователя с id {}", userId, eventId);
        User user = getUserById(userId);
        Event event = getEventById(eventId);

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            log.info("Подтверждение заявок на данное событие не требуется");
            throw new NotAvailableException("Подтверждение заявок на данное событие не требуется");
        }
        if (!event.getInitiator().equals(user)) {
            throw new IllegalArgumentException("Событие не принадлежит данному пользователю");
        }
        long confirmedRequests = requestRepository.countAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
        long freePlaces = event.getParticipantLimit() - confirmedRequests;

        RequestStatus status = RequestStatus.valueOf(String.valueOf(requestToStatusUpdate.getStatus()));
        if (status.equals(RequestStatus.CONFIRMED) && freePlaces <= 0) {
            throw new NotAvailableException("Заявки на участие в данном событии больше не принимаются");
        }
        List<Request> requests = requestRepository.findAllByEventIdAndEventInitiatorIdAndIdIn(eventId, userId, requestToStatusUpdate.getRequestIds());

        if (!requests.stream().map(Request::getStatus).allMatch(RequestStatus.PENDING::equals)) {
            throw new NotAvailableException("Изменять можно только запросы находящиеся в ожидании");
        }
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        List<ParticipationRequestDto> confirmedRequestsDto = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequestsDto = new ArrayList<>();
        result.setConfirmedRequests(confirmedRequestsDto);
        result.setRejectedRequests(rejectedRequestsDto);

        if (requestToStatusUpdate.getStatus().equals(REJECTED)) {
            requests.forEach(request -> request.setStatus(RequestStatus.REJECTED));
            result.setRejectedRequests(requestRepository.saveAll(requests).stream()
                    .map(request -> Mapper.mapToParticipationRequestDto(mapper, request)).collect(Collectors.toList()));
        }
        if (requestToStatusUpdate.getStatus().equals(CONFIRMED)) {
            for (Request r : requests) {
                if (requestRepository.findConfirmedRequests(eventId).equals(event.getParticipantLimit())) {
                    r.setStatus(RequestStatus.REJECTED);
                    result.getRejectedRequests().add(Mapper.mapToParticipationRequestDto(mapper, requestRepository.save(r)));
                } else {
                    r.setStatus(RequestStatus.CONFIRMED);
                    result.getConfirmedRequests().add(Mapper.mapToParticipationRequestDto(mapper, requestRepository.save(r)));
                }
            }
        }
        eventRepository.save(event);
        return result;
    }

    @Override
    public List<EventFullDto> getEventsByCondition(List<Long> users, List<EventsState> eventsStates, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        log.info("Admin: Получение полной информации о всех событиях по условиям");
        validationDateTime(rangeStart, rangeEnd);
        rangeStart = getRangeStart(rangeStart);
        rangeEnd = getRangeEnd(rangeEnd);
        PageRequest page = PageRequest.of(from / size, size);

        List<Event> events = eventRepository.findAllAdminByCondition(users, eventsStates, categories, rangeStart, rangeEnd, page);

        List<EventFullDto> eventsFull = events.stream().map(event -> mapper.map(event, EventFullDto.class)).collect(Collectors.toList());
        List<EventsConfirmedRequest> confirmedRequests = requestRepository.getCountConfirmedRequests();
        Map<Long, Long> mapConfirmedRequests = confirmedRequests
                .stream()
                .collect(Collectors.toMap(request -> request.getEventId(), request -> request.getCountConfirmedRequests()));

        eventsFull.forEach((eventFullDto -> {
            if (mapConfirmedRequests.containsKey(eventFullDto.getId())) {
                eventFullDto.setConfirmedRequests(mapConfirmedRequests.get(eventFullDto.getId()));
            } else {
                eventFullDto.setConfirmedRequests(0L);
            }
        }));
        Map<Long, Long> eventViews = getViews(events);
        eventsFull.forEach(e -> e.setViews(eventViews.get(e.getId())));

        return eventsFull;
    }

    @Override
    public EventFullDto editEventAndStatus(Long eventId, UpdateEventAdminRequest updateEventRequest) {
        log.info("Admin: Обновление события с id {}", eventId);

        Event event = getEventById(eventId);
        if (updateEventRequest.getEventDate() != null && updateEventRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ValidationDateException("Новая дата события раньше текущей");
        }
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
            throw new NotAvailableException("Время начала изменяемого события должно быть не раньше, чем за 1 час от даты публикации");
        }
        if (updateEventRequest.getStateAction() != null) {
            if (updateEventRequest.getStateAction().equals(PUBLISH_EVENT)) {
                if (!event.getEventsState().equals(EventsState.PENDING)) {
                    throw new NotAvailableException(String.format("Событие %s уже было опубликовано", eventId));
                }
                event.setEventsState(PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                if (!event.getEventsState().equals(EventsState.PENDING)) {
                    throw new NotAvailableException("Событие должно быть в статусе PENDING");
                }
                event.setEventsState(EventsState.CANCELED);
            }
        }
        patchUpdateEvent(updateEventRequest, event);
        locationRepository.save(event.getLocation());

        return mapper.map(eventRepository.save(event), EventFullDto.class);
    }

    @Override
    public List<EventShortDto> getAllEvents(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd, Boolean onlyAvailable, SortingOption sortingOption,
                                            int from, int size, HttpServletRequest httpServletRequest) {
        log.info("Public: Получение событий с возможностью фильтрации");
        rangeStart = getRangeStart(rangeStart);
        rangeEnd = getRangeEnd(rangeEnd);

        String paidStr = null;
        if (paid != null) {
            paidStr = paid ? "true" : "false";
        }
        validationDateTime(rangeStart, rangeEnd);
        PageRequest page = PageRequest.of(from / size, size);

        List<Event> events = eventRepository.findAllByPublic(text, categoryIds, paidStr, rangeStart, rangeEnd, page);
        List<EventShortDto> eventsShort = events.stream().map(event -> mapper.map(event, EventShortDto.class)).collect(Collectors.toList());
        List<EventsConfirmedRequest> confirmedRequests = requestRepository.getCountConfirmedRequests();

        Map<Long, Long> mapConfirmedRequests = confirmedRequests
                .stream()
                .collect(Collectors.toMap(request -> request.getEventId(), request -> request.getCountConfirmedRequests()));

        eventsShort.forEach((eventFullDto -> {
            if (mapConfirmedRequests.containsKey(eventFullDto.getId())) {
                eventFullDto.setConfirmedRequests(mapConfirmedRequests.get(eventFullDto.getId()));
            } else {
                eventFullDto.setConfirmedRequests(0L);
            }
        }));
        Map<Long, Long> eventViews = getViews(events);
        eventsShort.forEach(e -> e.setViews(eventViews.get(e.getId())));
        statClient.createStat(httpServletRequest);

        return eventsShort;
    }

    @Override
    public EventFullDto getEventByIdPublic(Long eventId, HttpServletRequest httpServletRequest) {
        log.info("Public: Получение информации об опубликованном событии");
        Event event = getEventById(eventId);
        if (!event.getEventsState().equals(PUBLISHED)) {
            throw new NotFoundException(String.format("Событие с id = %s не было опубликовано", eventId));
        }
        EventFullDto eventFullDto = mapper.map(event, EventFullDto.class);
        eventFullDto.setConfirmedRequests(requestRepository.findConfirmedRequests(eventFullDto.getId()));
        List<Event> events = List.of(event);
        Map<Long, Long> eventViews = getViews(events);
        eventFullDto.setViews(statClient.getView(eventFullDto.getId()));
        statClient.createStat(httpServletRequest);

        return eventFullDto;
    }

    private void patchUpdateEvent(UpdateEventRequest requestToUpdate, Event event) {
        if (requestToUpdate.getAnnotation() != null && !requestToUpdate.getAnnotation().isBlank()) {
            event.setAnnotation(requestToUpdate.getAnnotation());
        }
        if (requestToUpdate.getCategory() != null) {
            Category category = categoryRepository.findById(requestToUpdate.getCategory()).orElseThrow(() -> new NotFoundException(String.format("Category %s not found", requestToUpdate.getCategory())));
            event.setCategory(category);
        }
        if (requestToUpdate.getDescription() != null && !requestToUpdate.getDescription().isBlank()) {
            event.setDescription(requestToUpdate.getDescription());
        }
        if (requestToUpdate.getEventDate() != null) {
            event.setEventDate(requestToUpdate.getEventDate());
        }
        if (requestToUpdate.getLocation() != null) {
            event.setLocation(requestToUpdate.getLocation());
        }
        if (requestToUpdate.getPaid() != null) {
            event.setPaid(requestToUpdate.getPaid());
        }
        if (requestToUpdate.getParticipantLimit() != null) {
            event.setParticipantLimit(requestToUpdate.getParticipantLimit());
        }
        if (requestToUpdate.getRequestModeration() != null) {
            event.setRequestModeration(requestToUpdate.getRequestModeration());
        }
        if (requestToUpdate.getTitle() != null && !requestToUpdate.getTitle().isBlank()) {
            event.setTitle(requestToUpdate.getTitle());
        }
    }

    private long getEventViews(Map<Long, Long> eventViews, long id) {
        if (eventViews.containsKey(id)) {
            return eventViews.get(id);
        } else return 0L;
    }

    private void validationDateTime(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new ValidationDateException(MESSAGE_VALIDATION_START_AFTER_END);
            }
        }
    }

    private LocalDateTime getRangeStart(LocalDateTime rangeStart) {
        if (rangeStart == null) {
            return LocalDateTime.now();
        }
        return rangeStart;
    }

    private LocalDateTime getRangeEnd(LocalDateTime rangeEnd) {
        if (rangeEnd == null) {
            return LocalDateTime.now().plusYears(500);
        }
        return rangeEnd;
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));
    }

    private Map<Long, Long> getViews(Collection<Event> events) {
        List<String> uris = events
                .stream()
                .map(event -> "/events/" + event.getId())
                .collect(Collectors.toList());
        Comparator<Event> byPublishedOn2 = Comparator.comparing(event -> event.getPublishedOn());
        Optional<Event> earliestEvent = events.stream().filter(e -> e.getPublishedOn() != null).min(byPublishedOn2);
        Map<Long, Long> views = new HashMap<>();

        if (earliestEvent.isPresent()) {
            List<ViewStatsResponseDto> response = statClient
                    .getStats(earliestEvent.get().getPublishedOn(), LocalDateTime.now(), uris, true);

            response.forEach(dto -> {
                String uri = dto.getUri();
                String[] split = uri.split("/");
                String id = split[2];
                Long eventId = Long.parseLong(id);
                views.put(eventId, dto.getHits());
            });
        } else {
            events.forEach(e -> views.put(e.getId(), 0L));
        }

        return views;
    }
}
