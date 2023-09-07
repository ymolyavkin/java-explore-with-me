package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.UpdateEventAdminRequest;
import ru.practicum.ewm.enums.State;
import ru.practicum.ewm.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping(value = "/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEventsByCondition(@PathVariable List<Long> users,
                                                                   @RequestParam List<State> states,
                                                                   @RequestParam List<Long> categories,
                                                                   @RequestParam LocalDateTime rangeStart,
                                                                   @RequestParam LocalDateTime rangeEnd,
                                                                   @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                                   @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size) {
        log.info("Получен запрос на полную информацию обо всех событиях, подходящих под условия");

        return new ResponseEntity<>(eventService.getEventsByCondition(users, states, categories, rangeStart, rangeEnd, from, size), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable Long eventId,
                                                    @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Получен запрос на обновление события с id {}", eventId);

        return new ResponseEntity<>(eventService.editEventForPublished(eventId, updateEventAdminRequest), HttpStatus.OK);
    }
}
