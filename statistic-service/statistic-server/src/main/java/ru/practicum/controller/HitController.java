package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.service.HitService;
import ru.practicum.validator.Marker;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping(value = "/")
@Slf4j
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    @GetMapping("/stats")
    public List<ViewStatsResponseDto> getViewStatistics(
            @RequestParam(name = "start")
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam(name = "end")
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(name = "uris", required = false) List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);

        return hitService.getViewStatistics(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public ResponseHitDto addHit(@Validated(Marker.OnCreate.class) @RequestBody IncomingHitDto incomingHitDto) {
        log.info("Получен запрос на сохранение информации о том что эндпойнт запрашивали");
        incomingHitDto.setCreated(LocalDateTime.now());

        return hitService.addHit(incomingHitDto);
    }
    @GetMapping("/test")
    public ResponseEntity<String> getTest(@RequestParam(name = "pathVariable") String test) {
        log.info("Получен запрос на test");

        return new ResponseEntity<>("Answer from test", HttpStatus.OK);
    }
}
