package ru.practicum.ewm.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.request.RequestService;

import java.util.List;

@Controller
@RequestMapping(value = "/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequestByUser(@PathVariable Long userId) {
        log.info("Получен запрос на поиск заявок пользователя с id {} на участие в чужих событиях", userId);

        return new ResponseEntity<>(requestService.getRequestByUser(userId), HttpStatus.OK);
    }

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ParticipationRequestDto> addNewRequest(@PathVariable Long userId,
                                                                 @RequestParam Long eventId) {
        log.info("Получен запрос на добавление нового запроса пользователя с id {} на участие в событии с id {}", userId, eventId);

        return new ResponseEntity<>(requestService.addRequestsToParticipate(userId, eventId), HttpStatus.CREATED);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequests(@PathVariable Long userId, @PathVariable Long requestId) {

        log.info("Получен запрос на отмену заявки с id {} пользователя с id {}", requestId, userId);

        return new ResponseEntity<>(requestService.cancelRequest(userId, requestId), HttpStatus.OK);
    }
}