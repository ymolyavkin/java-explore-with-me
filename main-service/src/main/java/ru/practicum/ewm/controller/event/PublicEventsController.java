package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.enums.SortingOption;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping(value = "/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventsController {
    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllEvents(@RequestParam String text,
                                                                   @RequestParam List<Long> categories,
                                                                   @RequestParam Boolean paid,
                                                                   @RequestParam LocalDateTime rangeStart,
                                                                   @RequestParam LocalDateTime rangeEnd,
                                                                   @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                                   @RequestParam SortingOption sortingOption,
                                                                   @RequestParam(defaultValue = PAGE_DEFAULT_FROM)@PositiveOrZero int from,
                                                                   @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive int size) {
        log.info("Получен запрос на получение событий с возможностью фильтрации");

        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventShortDto> getEventById(@PathVariable Long id) {
        log.info("Получен запрос на получение события с id {}", id);

        return  null;
    }
}
