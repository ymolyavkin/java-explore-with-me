package ru.practicum.service;

import ru.practicum.dto.ResponseHitDto;
import ru.practicum.dto.IncomingHitDto;

import java.util.List;

public interface HitService {
    List<ResponseHitDto> getAllHits();

    ResponseHitDto getHitById(Long id);

    ResponseHitDto addHit(IncomingHitDto incomingHitDto);
}
