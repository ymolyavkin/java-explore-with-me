package ru.practicum.ewm.controller.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.service.comment.PublicCommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.Constants.PAGE_DEFAULT_FROM;
import static ru.practicum.util.Constants.PAGE_DEFAULT_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
@Validated
@Slf4j
public class PublicCommentController {
    private final PublicCommentService publicCommentService;

    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getAllComments(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        log.info("Получен запрос на получение всех комментариев, содержащих текст");
        return ResponseEntity.ok(publicCommentService.getCommentsByText(text, from, size));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<ResponseCommentDto>> getCommentsByEvent(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        log.info("Получен запрос на получение комментария для события c id = {}", eventId);
        return ResponseEntity.ok(publicCommentService.getCommentsByEvent(eventId, from, size));
    }
}
