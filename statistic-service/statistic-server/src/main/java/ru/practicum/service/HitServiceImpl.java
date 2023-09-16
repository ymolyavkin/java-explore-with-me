package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.entity.HitEntity;
import ru.practicum.mapper.HitMapper;
import ru.practicum.repository.HitRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_START_AFTER_END;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Transactional
    @Override
    public ResponseHitDto addHit(IncomingHitDto incomingHitDto) {
        HitEntity hitEntity = hitRepository.save(hitMapper.toHitEntity(incomingHitDto));

        return hitMapper.toResponseHitDto(hitEntity);
    }

    @Override
    public List<ViewStatsResponseDto> getViewStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            log.error(MESSAGE_VALIDATION_START_AFTER_END);
            throw new ValidationException(MESSAGE_VALIDATION_START_AFTER_END);
        }
        if (uris == null) {
            if (unique) {
                return hitRepository.getViewStatisticsWithUniqueIpAllUris(start, end);
            } else {
                return hitRepository.getViewStatisticsWithAllIpAllUris(start, end);
            }
        } else {
            if (unique) {
                return hitRepository.getViewStatisticsWithUniqueIp(start, end, uris);
            } else {
                return hitRepository.getViewStatisticsWithAllIp(start, end, uris);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getView(long eventId) {
        Long view = hitRepository.countDistinctByUri("/events/" + eventId);
        return Objects.requireNonNullElse(view, 0L);
    }
}
