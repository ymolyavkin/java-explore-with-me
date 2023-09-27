package ru.practicum.ewm.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.service.comment.AdminCommentServiceImpl;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@Validated
@Slf4j
public class AdminCommentController {
    private final AdminCommentServiceImpl adminCommentService;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        log.info("Получен запрос на удаление комментария");
        if (adminCommentService.deleteComment(commentId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Комментарий не найден или недоступен", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<ResponseCommentDto>> getAllByEvent(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        log.info("Получен запрос на получение всех комментариев события с id = {}", eventId);
        return ResponseEntity.ok(adminCommentService.getCommentsByEvent(eventId, from, size));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ResponseCommentDto>> getAllByAuthor(
            @PathVariable Long userId,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        log.info("Получен запрос на получение всех комментариев");
        return ResponseEntity.ok(adminCommentService.getCommentsByAuthor(userId, from, size));
    }
}
