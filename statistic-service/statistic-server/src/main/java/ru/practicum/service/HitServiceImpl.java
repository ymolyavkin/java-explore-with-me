package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.entity.HitEntity;
import ru.practicum.mapper.HitMapper;
import ru.practicum.repository.HitRepository;

import java.util.List;
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

    @Transactional
    @Override
    public ResponseHitDto addHit(IncomingHitDto incomingHitDto) {
        //  Booking booking = bookingRepository.save(resultBooking);
        HitEntity hitEntity = hitRepository.save(hitMapper.toHitEntity(incomingHitDto));
        ResponseHitDto responseHitDto = hitMapper.toResponseHitDto(hitEntity);
        System.out.println(hitEntity);
        return hitMapper.toResponseHitDto(hitEntity);
    }
}
