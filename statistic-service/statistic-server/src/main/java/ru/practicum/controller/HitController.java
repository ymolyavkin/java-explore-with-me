package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.service.HitService;
import ru.practicum.validator.Marker;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping(value = "/")
@Slf4j
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    //    @GetMapping("/stats")
//    public List<ResponseHitDto> getAllHits(
//            @RequestParam(name = "start")
//            @DateTimeFormat(pattern = DATE_TIME_PATTERN) String start,
//            @RequestParam(name = "end")
//            @DateTimeFormat(pattern = DATE_TIME_PATTERN) String end,
//            @RequestParam(name = "uris", required = false) String[] uris,
//            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
//        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
//
//        //return hitClient.getStatisticsOnHits(start, end, uris, unique);
//        return hitService.getAllHits();
//    }
    //ViewStatsDto
    @GetMapping("/stats")
    //public List<ViewStatsDto> getAllHits(
    public List<ViewStatsResponseDto> getViewStatistics(
            //  public List<ViewStatsResponse> getAllHits(
            @RequestParam(name = "start")
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam(name = "end")
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(name = "uris", required = false) String[] uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
        List<String> urisList = Arrays.asList(uris);
        return hitService.getViewStatistics(start, end, urisList, unique);
    }

    @PostMapping("/hit")
    //public ResponseEntity<Object> addHit(@Validated(Marker.OnCreate.class) @RequestBody IncomingHitDto incomingHitDto) {
    public ResponseHitDto addHit(@Validated(Marker.OnCreate.class) @RequestBody IncomingHitDto incomingHitDto) {
        log.info("Получен запрос на сохранение информации о том что эндпойнт запрашивали");
        incomingHitDto.setCreated(LocalDateTime.now());
        //return hitClient.addHit(incomingHitDto);
        return hitService.addHit(incomingHitDto);
    }
}
