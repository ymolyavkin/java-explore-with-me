package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.request.UpdateEventAdminRequest;
import ru.practicum.ewm.enums.EventsState;
import ru.practicum.ewm.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.*;

@Controller
@RequestMapping(value = "/admin/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEventsByCondition(@PathVariable(required = false) List<Long> users,
                                                                   @RequestParam(required = false) List<EventsState> eventsStates,
                                                                   @RequestParam(required = false) List<Long> categories,
                                                                   @RequestParam(required = false)
                                                                   @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                                                   @RequestParam(required = false)
                                                                   @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                                                   @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                                   @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size) {
        log.info("Получен запрос на полную информацию обо всех событиях, подходящих под условия");

        return ResponseEntity.ok(eventService.getEventsByCondition(users, eventsStates, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventAndStatus(@PathVariable Long eventId,
                                                             @Valid @RequestBody UpdateEventAdminRequest updateEventRequest) {
        log.info("Получен запрос на обновление события с id {}", eventId);

        return ResponseEntity.ok(eventService.editEventAndStatus(eventId, updateEventRequest));
    }
}
