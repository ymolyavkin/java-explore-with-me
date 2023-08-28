package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.entity.HitEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HitRepository extends JpaRepository<HitEntity, Long> {
    Optional<HitEntity> findById(Long id);

    @Query("select e from HitEntity e where e.id = :id")
    HitEntity getHitById(Long id);

    @Query("SELECT new ru.practicum.dto.ViewStatsResponseDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM HitEntity e " +
            "WHERE " +
            //   "e.uri IN :uris AND " +
            "e.created BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStatsResponseDto> getCountHits(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.ViewStatsResponseDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM HitEntity e " +
            "WHERE " +
         //   "e.uri IN :uris AND " +
            "e.created BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(DISTINCT e.ip) DESC")
    List<ViewStatsResponseDto> getViewStatisticsWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.ViewStatsResponseDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM HitEntity e " +
            "WHERE " +
          //  "e.uri IN :uris AND " +
            "e.created BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStatsResponseDto> getViewStatisticsWithAllIp(LocalDateTime start, LocalDateTime end);
}