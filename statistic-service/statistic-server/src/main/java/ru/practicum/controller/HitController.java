package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.validator.Marker;

@Controller
@RequestMapping(value = "/")
@Slf4j
@RequiredArgsConstructor
public class HitController {
    @GetMapping("/stats")
    public ResponseEntity<Object> getBookingsByOwner(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "uris", required = false) String[] uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);

        return null;
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@Validated(Marker.OnCreate.class) @RequestBody IncomingHitDto incomingHitDto) {
        log.info("Получен запрос на сохранение информации о том что эндпойнт запрашивали");
        return null;
        //return bookingClient.addBooking(bookerId, incomingBookingDto);
    }
}
