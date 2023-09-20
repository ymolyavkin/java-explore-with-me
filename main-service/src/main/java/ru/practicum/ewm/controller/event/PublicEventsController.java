package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.*;

@Controller
@RequestMapping(value = "/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllEvents(@RequestParam(required = false) String text,
                                                            @RequestParam(required = false) List<Long> categories,
                                                            @RequestParam(required = false) Boolean paid,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                            @RequestParam(required = false) SortingOption sortingOption,
                                                            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size,
                                                            HttpServletRequest httpServletRequest) {
        log.info("Получен запрос на получение событий с возможностью фильтрации");

        return ResponseEntity.ok(eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sortingOption, from, size, httpServletRequest));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable Long eventId, HttpServletRequest httpServletRequest) {
        log.info("Получен запрос на получение события с id {}", eventId);

        return ResponseEntity.ok(eventService.getEventByIdPublic(eventId, httpServletRequest));
    }
}
