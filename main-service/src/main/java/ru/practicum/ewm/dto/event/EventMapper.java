package ru.practicum.ewm.dto.event;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.dto.LocationDto;
import ru.practicum.ewm.dto.category.CategoryDto;
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

    public static Event mapToEventFullDto(Event event, CategoryDto categoryDto, Long confirmedRequests, LocationDto locationDto, UserShortDto initiator) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(categoryDto);
        eventFullDto.setConfirmedRequests(confirmedRequests);
        eventFullDto.setInitiator(initiator);
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        //eventFullDto.setLocation(locationDto);
        eventFullDto.setPaid(event.getPaid());
        //eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setState(event.getEventsState());
        event.setCreatedOn(event.getCreatedOn());
        event.setPublishedOn(event.getPublishedOn());

        return event;
    }
}
