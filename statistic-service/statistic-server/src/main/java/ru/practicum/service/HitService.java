package ru.practicum.service;

import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.entity.HitEntity;
import ru.practicum.entity.ViewStatsResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    List<ResponseHitDto> getAllHits();

    ResponseHitDto getHitById(Long id);

    List<ViewStatsResponseDto> getViewStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    //   List<ViewStatsDto> getCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
   // List<ViewStatsResponse> findCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);  //  List<ViewStatsResponseDto> getCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
    List<HitEntity> findHitEntity(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);  //  List<ViewStatsResponseDto> getCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
    //List<HitRepository.StatsDto> getCountHits2(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    ResponseHitDto addHit(IncomingHitDto incomingHitDto);
}
