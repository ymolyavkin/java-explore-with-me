package ru.practicum.mapper;

public interface EntityMapper<I, E, R> {
    E toHitEntity(I incomingDto);

    R toResponseHitDto(E entity);
}
