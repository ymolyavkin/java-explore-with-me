package ru.practicum.ewm.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;

@Controller
@RequestMapping(value = "/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class PrivateRequestController {
    @GetMapping
    public ResponseEntity<ParticipationRequestDto> getRequestByUser(@PathVariable Long userId) {
        log.info("Получен запрос на поиск заявок пользователя с id {} на участие в чужих событиях", userId);

        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addNewRequest(@PathVariable Long userId,
                                                @RequestParam Long eventId) {
        log.info("Получен запрос на добавление нового запроса пользователя с id {} на участие в событии с id {}", userId, eventId);

        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequests(@PathVariable Long userId, @RequestParam Long requestId) {

        log.info("Получен запрос на отмену заявки с id {} пользователя с id {}", requestId, userId);

        return null;
    }
}