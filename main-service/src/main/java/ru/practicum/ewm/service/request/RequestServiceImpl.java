package ru.practicum.ewm.service.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.repository.RequestRepository;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final ModelMapper mapper;

    @Override
    public List<ParticipationRequestDto> getRequestsToParticipate(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addRequestsToParticipate(Long userId, Long eventId) {
        return null;
    }

    @Override
    public CompilationDto cancellingRequest(Long userId, Long requestId) {
        return null;
    }
}
