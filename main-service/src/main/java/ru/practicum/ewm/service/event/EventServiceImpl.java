package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.ewm.client.Client;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.mapper.Mapper;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.request.UpdateEventRequest;
import ru.practicum.ewm.entity.*;
import ru.practicum.ewm.enums.EventsState;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.enums.StateAction;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationDateException;
import ru.practicum.ewm.repository.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.enums.EventsState.PUBLISHED;
import static ru.practicum.ewm.enums.RequestStatus.CONFIRMED;
import static ru.practicum.ewm.enums.RequestStatus.REJECTED;
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
        return events.stream().map(event -> mapper.map(event, EventShortDto.class)).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventFullAddedCurrentUser(Long userId, Long eventId) {
        log.info("Private: Получение полной информации о событии с id = {}, добавленном текущим пользователем с id = {}", eventId, userId);
        Event event = eventRepository.findByInitiator_IdAndAndId(userId, eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s, добавленное пользователем с id %s не найдено", eventId, userId)));

        return mapper.map(event, EventFullDto.class);
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        log.info("Private: Добавление нового события");
        User initiator = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new NotFoundException(String.format("Категория  %s не найдена", newEventDto.getCategory())));

        Location savedLocation = locationRepository.save(newEventDto.getLocation());

        if (this.mapper.getTypeMap(NewEventDto.class, Event.class) == null) {
            this.mapper.createTypeMap(NewEventDto.class, Event.class);
        }

        this.mapper.getTypeMap(NewEventDto.class, Event.class).addMappings((mapper) -> {
            mapper.skip(Event::setId);
            mapper.skip(Event::setCategory);
            mapper.skip(Event::setInitiator);
            mapper.skip(Event::setEventsState);
            mapper.skip(Event::setPublishedOn);
            mapper.skip(Event::setCreatedOn);
        });

        Event event = mapper.map(newEventDto, Event.class);

        event.setCreatedOn(LocalDateTime.now());
        event.setCategory(category);
        event.setInitiator(initiator);
        event.setEventsState(EventsState.PENDING);


        Event savedEvent = eventRepository.save(event);

        return mapper.map(savedEvent, EventFullDto.class);
    }

    @Override
    public EventFullDto editEventFromAuthor(Long userId, Long eventId, UpdateEventRequest eventToUpdate) {
        log.info("Private: Изменение события с id {}, добавленного пользователем с id {}", eventId, userId);

        Event event = getEventById(eventId);
        if (event.getEventsState().equals(PUBLISHED)) {
            throw new NotAvailableException("Изменить можно только отмененные события или события в состоянии ожидания модерации");
        }

        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));

        patchUpdateEvent(eventToUpdate, event);

        if (eventToUpdate.getStateAction() != null) {
            if (eventToUpdate.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
                event.setEventsState(EventsState.PENDING);
            } else {
                event.setEventsState(EventsState.CANCELED);
            }
        }

        return mapper.map(event, EventFullDto.class);
    }

    @Override
    public List<ParticipationRequestDto> getRequestToParticipationByUser(Long userId, Long eventId) {
        log.info("Private: Получение запросов на участие в событии с id {} пользователя с id {}", eventId, userId);
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));

//        eventRepository.findById(eventId)
//                .orElseThrow(() -> new NotFoundException(String.format("Событие %s не найдено", eventId)));
        getEventById(eventId);
       /* if (this.mapper.getTypeMap(Request.class, ParticipationRequestDto.class) == null) {
            this.mapper.createTypeMap(Request.class, ParticipationRequestDto.class);
        }
        TypeMap<Request, ParticipationRequestDto> eventMapper = this.mapper.createTypeMap(Request.class, ParticipationRequestDto.class);
        eventMapper.addMapping(Request::getId, ParticipationRequestDto::setEvent);*/

        return requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId).stream()
                //    .map(request -> mapper.map(request, ParticipationRequestDto.class))
                .map(request -> Mapper.mapToParticipationRequestDto(mapper, request)).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest requestToStatusUpdate) {
        log.info("Private: Изменение статуса заявок на участие в событии с id {} пользователя с id {}", userId, eventId);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));
        Event event = getEventById(eventId);

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            log.info("Подтверждение заявок на данное событие не требуется");
            throw new NotAvailableException("Подтверждение заявок на данное событие не требуется");
        }
        if (!event.getInitiator().equals(user)) {
            throw new IllegalArgumentException("Событие не принадлежит данному пользователю");
        }
        long confirmedRequests = requestRepository.countAllByEventIdAndStatus(eventId, CONFIRMED);
        long freePlaces = event.getParticipantLimit() - confirmedRequests;
        RequestStatus status = RequestStatus.valueOf(String.valueOf(requestToStatusUpdate.getStatus()));
        if (status.equals(CONFIRMED) && freePlaces <= 0) {
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
            requests.forEach(request -> request.setStatus(REJECTED));
            result.setRejectedRequests(requestRepository.saveAll(requests).stream()
                    .map(request -> Mapper.mapToParticipationRequestDto(mapper, request)).collect(Collectors.toList()));
        }
        //request -> Mapper.mapToParticipationRequestDto(mapper, request)
        if (requestToStatusUpdate.getStatus().equals(CONFIRMED)) {
            for (Request r : requests) {
                if (requestRepository.findConfirmedRequests(eventId).equals(event.getParticipantLimit())) {
                    r.setStatus(REJECTED);
                    result.getRejectedRequests().add(Mapper.mapToParticipationRequestDto(mapper, requestRepository.save(r)));
                } else {
                    r.setStatus(CONFIRMED);
                    result.getConfirmedRequests().add(Mapper.mapToParticipationRequestDto(mapper, requestRepository.save(r)));
                }
            }
        }
        eventRepository.save(event);
        return result;
    }

       /* Long confirmedRequests = requestRepository.countAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);

        long freePlaces = event.getParticipantLimit() - confirmedRequests;

        RequestStatus status = RequestStatus.valueOf(String.valueOf(requestToStatusUpdate.getStatus()));

        if (status.equals(RequestStatus.CONFIRMED) && freePlaces <= 0) {
            throw new NotAvailableException("Заявки на участие в данном событии больше не принимаются");
        }

        List<Request> requests = requestRepository.findAllByEventIdAndEventInitiatorIdAndIdIn(eventId, userId, requestToStatusUpdate.getRequestIds());
        List<ParticipationRequestDto> confirmedRequestsDto = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequestsDto = new ArrayList<>();

        // setStatus(requests, status, freePlaces);

       *//* List<ParticipationRequestDto> requestsDto = requests
                .stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());*//*

        for (Request request : requests) {
            if (freePlaces > 0) {
                confirmedRequestsDto.add(mapper.map(request, ParticipationRequestDto.class));
                freePlaces--;
            } else {
                rejectedRequestsDto.add(mapper.map(request, ParticipationRequestDto.class));
            }
        }

       *//* requestsDto.forEach(el -> {
            if (status.equals(RequestStatus.CONFIRMED)) {
                confirmedRequestsDto.add(el);
            } else {
                rejectedRequestsDto.add(el);
            }
        });*//*

        return new EventRequestStatusUpdateResult(confirmedRequestsDto, rejectedRequestsDto);*/


    @Override
    public List<EventFullDto> getEventsByCondition(List<Long> users, List<EventsState> eventsStates, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        log.info("Admin: Получение полной информации о всех событиях по условиям");
        validationDateTime(rangeStart, rangeEnd);
        rangeStart = getRangeStart(rangeStart);
        rangeEnd = getRangeEnd(rangeEnd);
        //  Page<Event> eventsPage = eventRepository.findAllForAdmin(users, eventsStates, categories, rangeStart,
        // List<Event> events = eventRepository.findAllForAdmin(users, eventsStates, categories, rangeStart,
        List<Event> events = eventRepository.findAllAdminByCondition(users, eventsStates, categories, rangeStart, rangeEnd, PageRequest.of(from, size));
        //  List<Event> events = eventsPage.getContent();

        return events.stream().map(event -> mapper.map(event, EventFullDto.class)).collect(Collectors.toList());
    }

    @Override
    public EventFullDto editEventAndStatus(Long eventId, UpdateEventRequest updateEventRequest) {
        log.info("Admin: Обновление события с id {}", eventId);

        Event event = getEventById(eventId);
        if (updateEventRequest.getStateAction() != null) {
            if (updateEventRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
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
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
            throw new NotAvailableException("Время начала изменяемого события должно быть не раньше, чем за 1 час от даты публикации");
        }
        patchUpdateEvent(updateEventRequest, event);
        locationRepository.save(event.getLocation());

        return mapper.map(event, EventFullDto.class);
    }

    @Override
    public List<EventShortDto> getAllEvents(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortingOption sortingOption, int from, int size, HttpServletRequest httpServletRequest) {
        log.info("Public: Получение событий с возможностью фильтрации");
        rangeStart = getRangeStart(rangeStart);
        rangeEnd = getRangeEnd(rangeEnd);
        /*if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(150);
        }*/
        String paidStr = null;
        if (paid != null) {
            paidStr = paid ? "true" : "false";
        }
        validationDateTime(rangeStart, rangeEnd);
        PageRequest page = PageRequest.of(from / size, size);

        List<Event> events = eventRepository.findAllByPublic(text, categoryIds, paidStr, rangeStart, rangeEnd, page);
        List<EventShortDto> eventsShort = events.stream().map(event -> mapper.map(event, EventShortDto.class)).collect(Collectors.toList());
        eventsShort.forEach(e -> e.setConfirmedRequests(requestRepository.findConfirmedRequests(e.getId())));
        // eventsShort.forEach(e -> e.setViews(statClient.getView(e.getId())));
        statClient.createStat(httpServletRequest);
        String[] uris = {"/events"};
        List<String> listUris = List.of("/events");
        var t = statClient.getStatisticsOnHits(rangeStart, rangeEnd, uris, false);

        return eventsShort;
    }

    @Override
    public EventFullDto getEventByIdPublic(Long eventId, HttpServletRequest httpServletRequest) {
        log.info("Public: Получение событий с возможностью фильтрации");
        Event event = getEventById(eventId);
        if (!event.getEventsState().equals(PUBLISHED)) {
            throw new NotFoundException(String.format("Событие с id = %s не было опубликовано", eventId));
        }
        EventFullDto eventFullDto = mapper.map(event, EventFullDto.class);
        // sendStats(httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr());
        //long viewsCount = statsClient.getCount();
        //  eventFullDto.setViews(viewsCount);
//        List<String> uris = List.of("/events/" + event.getId());
//        List<ViewStatsResponseDto> views = statsClient..getStats(START_DATE, END_DATE, uris, null).getBody();
//
//        if (views != null) {
//            eventFullDto.setViews(views.size());
//        }
//        statsClient.saveStats(APP, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());

//        log.info("Get event with  id = {}}", id);
        return eventFullDto;
//        public EventFullDto getEventByIdPublic(Long eventId, String uri, String ip) {
//            Event event = eventRepository.findById(eventId).orElseThrow(() ->
//                    new NotFoundException(String.format("Event %s not found", eventId)));
//
//            if (!event.getState().equals(EventStatus.PUBLISHED)) {
//                throw new NotFoundException(String.format("Event %s not published", eventId));
//            }
//
//            sendStats(uri, ip);
//
//            return mapToEventFullDto(List.of(event)).get(0);
//        }
    }

    private void sendStats(String uri, String ip) {
        IncomingHitDto incomingHitDto = new IncomingHitDto();
        incomingHitDto.setApp("main-service");
        incomingHitDto.setUri(uri);
        incomingHitDto.setIp(ip);
        incomingHitDto.setCreated(LocalDateTime.now());

        //statsClient.saveStats(incomingHitDto);
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
/*
private void updateEventCommonFields(Event event, EventUpdatedDto eventDto) {
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getCategory() != null) {
            Category category = getCategoryForEvent(eventDto.getCategory());
            event.setCategory(category);
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getLocation() != null) {
            event.setLat(eventDto.getLocation().getLat());
            event.setLon(eventDto.getLocation().getLon());
        }
        if (eventDto.getEventDate() != null) {
            validateEventDate(eventDto.getEventDate());
            event.setEventDate(eventDto.getEventDate());
        }
    }
 */
   /* private void setStatus(List<Request> requests, State state, long freePlaces) {
        if (state.equals(RequestStatus.CONFIRMED)) {
            for (Request request : requests) {
                if (!request.getStatus().equals(State.PENDING)) {
                    throw new NotAvailableException("Request's state has to be PENDING");
                }
                if (freePlaces > 0) {
                    request.setStatus(RequestStatus.CONFIRMED);
                    freePlaces--;
                } else {
                    request.setStatus(RequestStatus.REJECTED);
                }
            }
        } else if (state.equals(RequestStatus.REJECTED)) {
            requests.forEach(request -> {
                if (!request.getStatus().equals(RequestStatus.PENDING)) {
                    throw new NotAvailableException("Request's state has to be PENDING");
                }
                request.setStatus(RequestStatus.REJECTED);
            });
        } else {
            throw new NotAvailableException("You must either approve - CONFIRMED" +
                    " or reject - REJECTED the application");
        }
    }*/
}
