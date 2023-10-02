package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.dto.mapper.CommentMapper;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    @Override
    public List<ResponseCommentDto> getCommentsByEvent(Long eventId, int from, int size) {
        log.info("Public: Получение всех комментариев к событию с id = {}", eventId);
        Event event = getEventById(eventId);
        PageRequest page = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByEventIdOrderByCreated(eventId, page);

        return mapToListResponseComment(comments);
    }

    @Override
    public List<ResponseCommentDto> getCommentsByText(String text, int from, int size) {
        log.info("Public: Получение всех комментариев, содержащих текст");
        Page<Comment> commentPage;
        PageRequest page = PageRequest.of(from / size, size);
        if (text == null) {
            commentPage = commentRepository.findAll(page);
        } else {
            commentPage = commentRepository.findAllByTextOrderByCreated(text, page);
        }
        List<Comment> comments = commentPage.getContent();

        return mapToListResponseComment(comments);
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(String.format("Комментарий с id %s не найден", commentId)));
    }

    private List<ResponseCommentDto> mapToListResponseComment(List<Comment> comments) {
        return comments
                .stream()
                .map(comment -> CommentMapper.mapToResponseCommentDto(comment, mapper.map(comment.getAuthor(), UserShortDto.class), comment.getEvent().getId()))
                .collect(Collectors.toList());
    }
}
