package ru.practicum.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Map;

import static ru.practicum.util.Constants.*;

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
    @GetMapping("/view/{eventId}")
    public Long getView(@PathVariable long eventId) {
        return hitService.getView(eventId);
    }
    @GetMapping("/viewstats")
    public List<ViewStatsResponseDto> getViewStats(
            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            @RequestParam(name = "start")
            @DateTimeFormat(pattern = FORMAT_PATTERN) LocalDateTime start,
            @JsonSerialize(using = LocalDateSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            @RequestParam(name = "end")
            @DateTimeFormat(pattern = FORMAT_PATTERN) LocalDateTime end,
            @RequestParam(name = "uris", required = false) List<String> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {

        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
        List<ViewStatsResponseDto> listDto = hitService.getViewStatistics(start, end, uris, unique);
        return hitService.getViewStatistics(start, end, uris, unique);
    }

    @GetMapping("/custom")
    public ResponseEntity<String> controllerMethod(@RequestParam Map<String, String> customQuery) {
        try {
            LocalDateTime startTime = LocalDateTime.parse(customQuery.get("start"), FORMATTER_DATE);
            LocalDateTime endTime = LocalDateTime.parse(customQuery.get("end"), FORMATTER_DATE).withNano(0);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            log.info("ошибка в формате даты");
        }
        return null;
    }
}
