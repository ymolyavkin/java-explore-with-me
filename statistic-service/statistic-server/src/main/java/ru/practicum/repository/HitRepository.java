package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.entity.HitEntity;

import java.time.LocalDateTime;
import java.util.List;
/*
(:name is null or c.name = :name)
and (coalesce(:unitNames) is null or r.deliveryUnit.unitName in (:unitNames) )"
 */
public interface HitRepository extends JpaRepository<HitEntity, Long> {
    @Query("SELECT new ru.practicum.dto.ViewStatsResponseDto(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM HitEntity e " +
            "WHERE " +
            "COALESCE(:uris) IS NULL OR e.uri IN (:uris) AND " +
            "e.created BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(DISTINCT e.ip) DESC")
    List<ViewStatsResponseDto> getViewStatisticsWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.ViewStatsResponseDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM HitEntity e " +
            "WHERE " +
            "COALESCE(:uris) IS NULL OR e.uri IN (:uris) AND " +
            "e.created BETWEEN :start and :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStatsResponseDto> getViewStatisticsWithAllIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}