package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.dto.comment.ResponseCommentDto;

import java.util.List;

public interface AdminCommentService {
    boolean deleteComment(Long commentId);

    List<ResponseCommentDto> getCommentsByEvent(Long eventId, int from, int size);

    List<ResponseCommentDto> getCommentsByAuthor(Long userId, int from, int size);
}
