package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.enums.SortingOption;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@Controller
@RequestMapping(value = "/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventsController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllEvents(@RequestParam String text,
                                                            @RequestParam List<Long> categories,
                                                            @RequestParam Boolean paid,
                                                            @RequestParam LocalDateTime rangeStart,
                                                            @RequestParam LocalDateTime rangeEnd,
                                                            @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                            @RequestParam SortingOption sortingOption,
                                                            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero int from,
                                                            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size,
                                                            HttpServletRequest httpServletRequest) {
        log.info("Получен запрос на получение событий с возможностью фильтрации");

        return new ResponseEntity<>(eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sortingOption, from, size, httpServletRequest), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable Long eventId, HttpServletRequest httpServletRequest) {
        log.info("Получен запрос на получение события с id {}", eventId);

        return new ResponseEntity<>(eventService.getEventByIdPublic(eventId, httpServletRequest), HttpStatus.OK);
    }
}
