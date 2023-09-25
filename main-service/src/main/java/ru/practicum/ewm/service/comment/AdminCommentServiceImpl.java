package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.dto.mapper.CommentMapper;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.MESSAGE_USER_IS_NOT_AUTHOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public boolean deleteComment(Long commentId) {
        log.info("Admin: Удаление комментария с id = {}", commentId);
        if (commentRepository.existsById(commentId)) {
            Comment comment = getCommentById(commentId);
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    @Override
    public List<ResponseCommentDto> getCommentsByEvent(Long eventId, int from, int size) {
        log.info("Admin: Получение комментариев к событию с id = {}", eventId);
        getEventById(eventId);
        PageRequest page = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByEventOrderByCreated(eventId, page);

        return mapToListResponseComment(comments);
    }

    @Override
    public List<ResponseCommentDto> getCommentsByAuthor(Long userId, int from, int size) {
        log.info("Admin: Получение комментариев пользователя с id = {}", userId);
        getUserById(userId);
        PageRequest page = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByAuthorOrderByCreated(userId, page);

        return mapToListResponseComment(comments);
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));
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
