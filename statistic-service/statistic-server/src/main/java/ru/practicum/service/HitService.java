package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ViewStatsResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    List<ViewStatsResponseDto> getViewStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    ResponseHitDto addHit(IncomingHitDto incomingHitDto);

    @Transactional(readOnly = true)
    Long getView(long eventId);
}
