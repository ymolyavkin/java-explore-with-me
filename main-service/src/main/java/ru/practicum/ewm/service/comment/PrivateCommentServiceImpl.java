package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.dto.mapper.CommentMapper;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.Comment;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.EventsState;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.MESSAGE_EVENT_IS_NOT_PUBLISHED;
import static ru.practicum.util.Constants.MESSAGE_USER_IS_NOT_AUTHOR;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public ResponseCommentDto createComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        log.info("Private: Добавление нового комментария к событию с id = {} пользователем с id = {}", eventId, userId);
        Event event = getEventById(eventId);
        if (event.getEventsState() != EventsState.PUBLISHED) {
            throw new NotAvailableException(MESSAGE_EVENT_IS_NOT_PUBLISHED);
        }
        User user = getUserById(userId);
        Comment comment = CommentMapper.mapToComment(newCommentDto, user, event);

        return CommentMapper.mapToResponseCommentDto(commentRepository.save(comment), mapper.map(user, UserShortDto.class), eventId);
    }

    @Override
    @Transactional
    public ResponseCommentDto patchComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        log.info("Private: Изменение комментария с id = {} пользователем с id = {}", commentId, userId);
        Comment comment = getCommentById(commentId);
        User user = getUserById(userId);
        checkCommentsAuthor(comment, user);
        comment.setText(newCommentDto.getText());

        return CommentMapper.mapToResponseCommentDto(commentRepository.save(comment), mapper.map(user, UserShortDto.class), comment.getEvent().getId());
    }

    @Override
    public List<ResponseCommentDto> getAllCommentsByUser(Long userId, int from, int size) {
        log.info("Private: Получение всех комментариев пользователя с id = {}", userId);
        User user = getUserById(userId);
        PageRequest page = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByAuthorOrderByCreated(userId, page);

        return comments
                .stream()
                .map(comment -> CommentMapper.mapToResponseCommentDto(comment, mapper.map(user, UserShortDto.class), comment.getEvent().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteCommentByUser(Long userId, Long commentId) {
        log.info("Private: Удаление комментария с id = {} пользователем с id = {}", commentId, userId);
        if (commentRepository.existsById(commentId)) {
            Comment comment = getCommentById(commentId);
            User user = getUserById(userId);
            checkCommentsAuthor(comment, user);
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    private void checkCommentsAuthor(Comment comment, User user) {
        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new NotAvailableException(MESSAGE_USER_IS_NOT_AUTHOR);
        }
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(String.format("Комментарий с id %s не найден", commentId)));
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));
    }
}
