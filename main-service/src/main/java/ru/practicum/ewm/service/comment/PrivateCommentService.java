package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;

import java.util.List;

public interface PrivateCommentService {
    ResponseCommentDto createComment(Long userId, Long eventId, NewCommentDto newCommentDto);
    ResponseCommentDto patchComment(Long userId, Long commentId, NewCommentDto newCommentDto);
    List<ResponseCommentDto> getAllCommentsByUser(Long userId, int from, int size);
    boolean deleteCommentByUser(Long userId, Long commentId);
}
