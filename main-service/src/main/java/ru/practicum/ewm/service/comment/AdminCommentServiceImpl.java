package ru.practicum.ewm.service.comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService{
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    @Override
    public boolean deleteComment(Long commentId) {
        return false;
    }

    @Override
    public List<ResponseCommentDto> getCommentsByEvent(Long eventId, int from, int size) {
        return null;
    }

    @Override
    public List<ResponseCommentDto> getCommentsByAuthor(Long userId, int from, int size) {
        return null;
    }
    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь %s не найден", userId)));
    }
}
