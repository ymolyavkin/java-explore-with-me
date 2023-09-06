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
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.request.UpdateEventAdminRequest;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.enums.State;
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
    public List<EventFullDto> getEventFullByIdByOwner(Long userId, Long eventId) {
        log.info("Private: Получение полной информации о событии с id = {}, добавленном текущим пользователем с id = {}", eventId, userId);
        return null;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
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
    public EventFullDto editEventFromAuthor(Long userId, Long eventId, NewEventDto newEventDto) {
        log.info("Private: Изменение события с id {}, добавленного пользователем с id {}", eventId, userId);
        return null;
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
        return null;
    }

    @Override
    public EventFullDto editEventForPublished(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
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
        return null;
    }

    @Override
    public EventShortDto getEventById(Long eventId) {
        return null;
    }
    /*
    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsAddedByCurrentUser(Long userId, Pageable page) {
        List<Event> events = eventRepository.findAllByInitiator_Id(userId, page);

        return mapToEventShortDto(events);
    }
     */
}
