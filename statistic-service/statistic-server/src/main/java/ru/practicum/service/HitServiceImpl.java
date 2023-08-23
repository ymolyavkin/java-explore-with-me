package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitResponseDto;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.repository.HitRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public List<HitResponseDto> getAll() {
        return null;
    }

    @Override
    public HitResponseDto getHitById(Long id) {
        return null;
    }

    @Override
    public HitResponseDto addBooking(IncomingHitDto incomingHitDto) {
        return null;
    }
}
