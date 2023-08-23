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
            //  @RequestHeader(value = USER_ID_FROM_REQUEST) Long ownerId,
            //     @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "uris", required = false) String[] uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
        //State stateValue = State.fromString(state)
        //        .orElseThrow(() -> new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS"));
        return null;
    }
    @PostMapping
    public ResponseEntity<Object> addHit(//@RequestHeader(value = USER_ID_FROM_REQUEST) Long bookerId,
                                            // @Valid
                                            // @StartBeforeEndValidation(message = "Дата окончания не может быть раньше или совпадать с датой начала")
                                             @Validated(Marker.OnCreate.class) @RequestBody IncomingHitDto incomingHitDto) {
        log.info("Получен запрос на сохранение информации о том что эндпойнт запрашивали");
return null;
        //return bookingClient.addBooking(bookerId, incomingBookingDto);
    }
}
