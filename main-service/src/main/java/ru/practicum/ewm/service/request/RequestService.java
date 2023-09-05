package ru.practicum.ewm.service.request;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequestsToParticipate(Long userId);

    ParticipationRequestDto addRequestsToParticipate(Long userId, Long eventId);

    CompilationDto cancellingRequest(Long userId, Long requestId);
}
