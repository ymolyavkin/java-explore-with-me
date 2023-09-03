package ru.practicum.ewm.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;

@RestController
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

    @PatchMapping("/users/{userId}/requests/{userId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequests(@PathVariable Long userId, @RequestParam Long eventId) {

        log.info("Получен запрос на отмену заявки на участие в событии с id {} пользователя с id {}", eventId, userId);

        return null;
    }
}
/*


    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getEventByIdByUser(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос на полную информацию о событии с id {}, добавленном пользователем с id {}", eventId, userId);

        return null;
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> getEventByIdByUserParticipation(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос на информацию о запросах на участие в событии с id {} пользователя с id {}", eventId, userId);

        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addNewEvent(@PathVariable Long userId,
                                              @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос на добавление нового события пользователем с id {}", userId);

        return null;
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable Long userId,
                                              @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос на обновление события пользователем с id {}", userId);

        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<Object> changeStatusRequests(
            @PathVariable Long userId, @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        log.info("Получен запрос на изменение статуса заявок на участие в событии с id {} пользователя с id {}", userId, eventId);

        return null;
    }
 */