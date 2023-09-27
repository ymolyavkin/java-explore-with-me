package ru.practicum.ewm.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.service.comment.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
@Validated
@Slf4j
public class PrivateCommentController {
    private final PrivateCommentService privateCommentService;

    @PostMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseCommentDto> createComment(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Получен запрос от пользователя {} на создание комментария id = {}", userId, eventId);
        return new ResponseEntity<>(privateCommentService.createComment(userId, eventId, newCommentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ResponseCommentDto> patchComment(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Получен запрос от пользователя {} на обновление комментария id = {}", userId, commentId);
        return ResponseEntity.ok(privateCommentService.patchComment(userId, commentId, newCommentDto));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<ResponseCommentDto>> getAll(
            @PathVariable Long userId,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        log.info("Получен запрос от пользователя {} на получение всех его комментариев", userId);
        return ResponseEntity.ok(privateCommentService.getAllCommentsByUser(userId, from, size));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> delete(@PathVariable Long userId,
                                         @PathVariable Long commentId) {
        log.info("Получен запрос от пользователя {} на удаление комментария id = {}", userId, commentId);
        if (privateCommentService.deleteCommentByUser(userId, commentId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Комментарий не найден или недоступен", HttpStatus.NOT_FOUND);
        }
    }

}
