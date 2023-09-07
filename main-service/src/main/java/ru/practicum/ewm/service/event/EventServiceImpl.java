package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.request.*;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.ChangeEventState;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.enums.State;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.LocationRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper mapper;

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
        Event event = eventRepository.findByInitiator_IdAndAndId(userId, eventId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Событие с id %s, добавленное пользователем с id %s не найдено", eventId, userId)));

        return mapper.map(event, EventFullDto.class);
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        log.info("Private: Добавление нового события");
        User initiator = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь %s не найден", userId)));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new NotFoundException(String.format("Категория  %s не найдена", newEventDto.getCategory())));

        Location savedLocation = locationRepository.save(newEventDto.getLocation());

        if (this.mapper.getTypeMap(NewEventDto.class, Event.class) == null) {
            this.mapper.createTypeMap(NewEventDto.class, Event.class);
        }

        this.mapper.getTypeMap(NewEventDto.class, Event.class).addMappings((mapper) -> {
            mapper.skip(Event::setId);
            mapper.skip(Event::setCategory);
            mapper.skip(Event::setInitiator);
            mapper.skip(Event::setState);
            mapper.skip(Event::setPublishedOn);
            mapper.skip(Event::setCreatedOn);
        });

        Event event = mapper.map(newEventDto, Event.class);

        event.setCreatedOn(LocalDateTime.now());
        event.setCategory(category);
        event.setInitiator(initiator);
        event.setState(State.PENDING);


        Event savedEvent = eventRepository.save(event);

        return mapper.map(savedEvent, EventFullDto.class);
    }

    @Override
    public EventFullDto editEventFromAuthor(Long userId, Long eventId, UpdateEventUserRequest eventToUpdate) {
        log.info("Private: Изменение события с id {}, добавленного пользователем с id {}", eventId, userId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));

        if (event.getState().equals(State.PUBLISHED)) {
            throw new NotAvailableException("Изменить можно только отмененные события или события в состоянии ожидания модерации");
        }

        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь %s не найден", userId)));

        patchUpdateEvent(eventToUpdate, event);

        if (eventToUpdate.getStateAction() != null) {
            if (eventToUpdate.getStateAction().equals(ChangeEventState.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            } else {
                event.setState(State.CANCELED);
            }
        }

        return mapper.map(event, EventFullDto.class);
    }

    @Override
    public List<ParticipationRequestDto> getRequestToParticipationByUser(Long userId, Long eventId) {
        log.info("Private: Получение запросов на участие в событии с id {} пользователя с id {}", eventId, userId);
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Private: Изменение статуса заявок на участие в событии с id {} пользователя с id {}", userId, eventId);
        return null;
    }

    @Override
    public List<EventFullDto> getEventsByCondition(List<Long> users,
                                                   List<State> states,
                                                   List<Long> categories,
                                                   LocalDateTime rangeStart,
                                                   LocalDateTime rangeEnd,
                                                   int from,
                                                   int size) {
        log.info("Admin: Получение полной информации о всех событиях по условиям");
        return null;
    }

    @Override
    public EventFullDto editEventForPublished(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Admin: Обновление события с id {}", eventId);
        return null;
    }

    @Override
    public List<EventShortDto> getAllEvents(String text,
                                            List<Long> categories,
                                            Boolean paid,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            boolean onlyAvailable,
                                            SortingOption sortingOption,
                                            int from,
                                            int size) {
        log.info("Public: Получение событий с возможностью фильтрации");
        return null;
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        log.info("Public: Получение событий с возможностью фильтрации");

        return null;
    }

    private void patchUpdateEvent(UpdateEventUserRequest requestToUpdate, Event event) {
        if (requestToUpdate.getAnnotation() != null && !requestToUpdate.getAnnotation().isBlank()) {
            event.setAnnotation(requestToUpdate.getAnnotation());
        }
        if (requestToUpdate.getCategory() != null) {
            Category category = categoryRepository.findById(requestToUpdate.getCategory()).orElseThrow(() ->
                    new NotFoundException(String.format("Category %s not found", requestToUpdate.getCategory())));
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
}
