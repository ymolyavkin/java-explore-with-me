package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.request.UpdateEventAdminRequest;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.enums.State;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.LocationRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<EventShortDto> getEventsByOwner(Long userId) {
        return null;
    }

    @Override
    public List<EventFullDto> getEventFullByIdByOwner(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь %s не найден", userId)));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new NotFoundException(String.format("Категория  %s не найдена", newEventDto.getCategory())));

        Location savedLocation = locationRepository.save(newEventDto.getLocation());

        TypeMap<NewEventDto, Event> propertyMapperToEvent = this.mapper.createTypeMap(NewEventDto.class, Event.class);
        propertyMapperToEvent.addMappings(mapper -> mapper.skip(Event::setId));
        propertyMapperToEvent.addMappings(mapper -> mapper.skip(Event::setCategory));
        propertyMapperToEvent.addMappings(mapper -> mapper.skip(Event::setInitiator));
        propertyMapperToEvent.addMappings(mapper -> mapper.skip(Event::setState));
        propertyMapperToEvent.addMappings(mapper -> mapper.skip(Event::setCreatedOn));
        propertyMapperToEvent.addMappings(mapper -> mapper.skip(Event::setPublishedOn));

        Event event = mapper.map(newEventDto, Event.class);

        event.setCreatedOn(LocalDateTime.now());
        event.setCategory(category);
        event.setInitiator(initiator);
        event.setState(State.PENDING);


        Event savedEvent = eventRepository.save(event);

        return mapper.map(savedEvent, EventFullDto.class);
    }

    @Override
    public EventShortDto editEventFromOwner(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequestToParticipationByUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
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
    public List<EventShortDto> getAllEventsPublic(String text,
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
    public EventShortDto getEventByIdPublic(Long id) {
        return null;
    }
}
