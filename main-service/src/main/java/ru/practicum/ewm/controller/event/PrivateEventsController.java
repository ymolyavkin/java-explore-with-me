package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping(value = "/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class PrivateEventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEventsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size) {
        log.info("Получен запрос на поиск событий, добавленных пользователем с id {}", userId);

        return new ResponseEntity<>(eventService.getEventsByOwner(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getEventByIdByUser(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос на полную информацию о событии с id {}, добавленном пользователем с id {}", eventId, userId);

        return null;
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> getEventByIdByUserParticipation(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос на информацию о запросах на участие в событии с id {} пользователя с id {}", eventId, userId);

        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventFullDto> addNewEvent(@PathVariable Long userId,
                                                    @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос на добавление нового события пользователем с id {}", userId);

        return new ResponseEntity<>(eventService.addEvent(userId, newEventDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> changeEventByAuthor(@PathVariable Long userId,
                                                      @PathVariable Long eventId,
                                                      @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос на изменение события с id {}, добавленного пользователем с id {}", eventId, userId);

        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<Object> changeStatusRequests(
            @PathVariable Long userId, @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        log.info("Получен запрос на изменение статуса заявок на участие в событии с id {} пользователя с id {}", userId, eventId);

        return null;
    }
}
