package ru.practicum.ewm.service.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.mapper.Mapper;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Request;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.EventsState;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.RequestRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    @Override
    public List<ParticipationRequestDto> getRequestsToParticipate(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addRequestsToParticipate(Long userId, Long eventId) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь %s не найден", userId)));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие %s не найдено", eventId)));

        if (event.getInitiator().getId().equals(requester.getId())) {
            throw new NotAvailableException("Инициатор события не может добавлять запросы на участие в своем событии");
        }
        if (!event.getEventsState().equals(EventsState.PUBLISHED)) {
            throw new NotAvailableException("Нелзя участвовать в неопубликованном событии");
        }

        Long confirmedRequests = requestRepository.countAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);

        if (event.getParticipantLimit() <= confirmedRequests && event.getParticipantLimit() != 0) {
            throw new NotAvailableException(("Достигнут лимит запросов на участие в данном событии"));
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(requester)
                .status(!event.getRequestModeration() || event.getParticipantLimit() == 0
                        ? RequestStatus.CONFIRMED
                        : RequestStatus.PENDING)
                .build();

        requestRepository.save(request);

        return Mapper.mapToParticipationRequestDto(mapper, request);
    }

    @Override
    public CompilationDto cancellingRequest(Long userId, Long requestId) {
        return null;
    }
}
