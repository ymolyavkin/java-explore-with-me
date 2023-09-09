package ru.practicum.ewm.service.event;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.request.UpdateEventRequest;
import ru.practicum.ewm.enums.EventsState;
import ru.practicum.ewm.enums.SortingOption;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEventsByOwner(Long userId, int from, int size);

    EventFullDto getEventFullAddedCurrentUser(Long userId, Long eventId);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto editEventFromAuthor(Long userId, Long eventId, UpdateEventRequest eventToUpdate);

    List<ParticipationRequestDto> getRequestToParticipationByUser(Long userId, Long eventId);

    EventRequestStatusUpdateResult changeStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    List<EventFullDto> getEventsByCondition(List<Long> users,
                                            List<EventsState> eventsStates,
                                            List<Long> categories,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            int from,
                                            int size);

    EventFullDto editEventAndStatus(Long eventId, UpdateEventRequest updateEventRequest);

    List<EventShortDto> getAllEvents(String text,
                                     List<Long> categories,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     boolean onlyAvailable,
                                     SortingOption sortingOption,
                                     int from,
                                     int size,
                                     HttpServletRequest httpServletRequest);

    EventFullDto getEventByIdPublic(Long id, HttpServletRequest httpServletRequest);
}
