package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.entity.HitEntity;
import ru.practicum.mapper.HitMapper;
import ru.practicum.repository.HitRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_START_AFTER_END;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    public List<ResponseHitDto> getAllHits() {
        List<HitEntity> hits = hitRepository.findAll();
        return hits.stream().map(hitEntity -> hitMapper.toResponseHitDto(hitEntity)).collect(Collectors.toList());
    }

    @Override
    public ResponseHitDto getHitById(Long id) {
        HitEntity hitEntity = hitRepository.getReferenceById(id);
        return hitMapper.toResponseHitDto(hitEntity);
    }

    @Transactional
    @Override
    public ResponseHitDto addHit(IncomingHitDto incomingHitDto) {
        HitEntity hitEntity = hitRepository.save(hitMapper.toHitEntity(incomingHitDto));
        ResponseHitDto responseHitDto = hitMapper.toResponseHitDto(hitEntity);
        System.out.println(hitEntity);
        return hitMapper.toResponseHitDto(hitEntity);
    }

    @Override
    public List<ViewStatsResponseDto> getViewStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            log.error(MESSAGE_VALIDATION_START_AFTER_END);
            throw new ValidationException(MESSAGE_VALIDATION_START_AFTER_END);
        }
        if (unique) {
            return hitRepository.getViewStatisticsWithUniqueIp(start, end);
        } else {
            return hitRepository.getViewStatisticsWithAllIp(start, end);
        }
    }

    @Override
    public List<HitEntity> findHitEntity(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        //List<HitEntity> countHits = hitRepository.getListHitEntity(start, end, uris);
        //   List<HitEntity> countHits = hitRepository.getListHitEntity();
        Optional<HitEntity> hitEntityOptional = hitRepository.findById(5L);
        HitEntity hitEntity = hitRepository.getHitById(8L);
        // List<ViewStatsResponse> countHits = hitRepository.getCountHits(start, end, uris);
        List<ViewStatsResponseDto> countHits = hitRepository.getCountHits(start, end);
        System.out.println();
        // return countHits;
        return null;
    }
}
