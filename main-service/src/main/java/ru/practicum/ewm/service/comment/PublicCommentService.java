package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.dto.comment.ResponseCommentDto;

import java.util.List;

public interface PublicCommentService {
    List<ResponseCommentDto> getCommentsByEvent(Long eventId, int from, int size);

    List<ResponseCommentDto> getCommentsByText(String text, int from, int size);
}
