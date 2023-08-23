package ru.practicum.service;

import ru.practicum.dto.HitResponseDto;
import ru.practicum.dto.IncomingHitDto;

import java.util.List;

public interface HitService {
    List<HitResponseDto> getAll();

    HitResponseDto getHitById(Long id);

    HitResponseDto addBooking(IncomingHitDto incomingHitDto);
}
