package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.comment.ResponseCommentDto;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final ModelMapper mapper;
    @Override
    public List<ResponseCommentDto> getCommentsByEvent(Long eventId, int from, int size) {
        return null;
    }

    @Override
    public List<ResponseCommentDto> getCommentsByText(String text, int from, int size) {
        return null;
    }
    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с id %s не найдено", eventId)));
    }
}
