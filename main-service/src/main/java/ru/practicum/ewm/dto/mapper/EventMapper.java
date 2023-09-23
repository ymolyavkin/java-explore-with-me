package ru.practicum.ewm.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.dto.LocationDto;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.EventsState;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public Event mapToEvent(NewEventDto newEventDto, Category category, Location location, User initiator) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(location);
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setInitiator(initiator);
        event.setEventsState(EventsState.PENDING);
        event.setCreatedOn(LocalDateTime.now());

        return event;
    }

    public EventFullDto mapToEventFullDto(Event event, CategoryDto categoryDto, Long confirmedRequests,
                                                 LocationDto locationDto, UserShortDto initiator, Long views) {
        EventFullDto eventFullDto = EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(confirmedRequests)
                .initiator(initiator)
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(locationDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .state(event.getEventsState())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .views(views)
                .build();

        return eventFullDto;
    }
}
