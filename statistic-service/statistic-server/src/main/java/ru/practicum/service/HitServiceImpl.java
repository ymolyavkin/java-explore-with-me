package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.entity.HitEntity;
import ru.practicum.entity.ViewStatsResponse;
import ru.practicum.mapper.HitMapper;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    @Override
//    public List<ViewStatsDto> getCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
//        return null;
//    }

    @Transactional
    @Override
    public ResponseHitDto addHit(IncomingHitDto incomingHitDto) {
        HitEntity hitEntity = hitRepository.save(hitMapper.toHitEntity(incomingHitDto));
        ResponseHitDto responseHitDto = hitMapper.toResponseHitDto(hitEntity);
        System.out.println(hitEntity);
        return hitMapper.toResponseHitDto(hitEntity);
    }
   /* @Override
    public List<ViewStatsResponse> findCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
    //public List<ViewStatsResponseDto> getCountHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
   // public List<HitRepository.StatsDto> getCountHits2(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
       // List<ViewStatsDto> countHits = hitRepository.collectHitsStatistic(start, end, uris);
       // List<ViewStatsResponseDto> countHits = hitRepository.countHits(start, end, uris);
       List<ViewStatsResponse> countHits = hitRepository.collectionStatistic(start, end, uris);
        System.out.println();
        return countHits;
    }*/

    @Override
    public List<HitEntity> findHitEntity(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        //List<HitEntity> countHits = hitRepository.getListHitEntity(start, end, uris);
     //   List<HitEntity> countHits = hitRepository.getListHitEntity();
      Optional<HitEntity> hitEntityOptional = hitRepository.findById(5L);
        Optional<HitEntity> hitEntity = hitRepository.getHitById(8L);
        System.out.println();
       // return countHits;
        return null;
    }
}
