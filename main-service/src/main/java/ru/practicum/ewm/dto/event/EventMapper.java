package ru.practicum.ewm.dto.event;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.entity.Category;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.EventsState;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public static Event mapToEvent(NewEventDto newEventDto, Category category, Location location, User initiator) {
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
}
