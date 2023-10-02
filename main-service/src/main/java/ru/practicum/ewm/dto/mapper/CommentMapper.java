package ru.practicum.ewm.dto.mapper;

import java.time.LocalDateTime;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.User;

@UtilityClass
public class CommentMapper {
    public Comment mapToComment(NewCommentDto newCommentDto, User author, Event event) {
        Comment comment = Comment.builder()
                .text(newCommentDto.getText())
                .created(LocalDateTime.now())
                .author(author)
                .event(event)
                .build();

        return comment;
    }

    public ResponseCommentDto mapToResponseCommentDto(Comment comment, UserShortDto author, Long eventId) {
        ResponseCommentDto responseCommentDto = ResponseCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .author(author)
                .eventId(eventId)
                .build();

        return responseCommentDto;
    }
}
