package ru.practicum.ewm.service.request;

import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto addRequestsToParticipate(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestByUser(Long userId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
