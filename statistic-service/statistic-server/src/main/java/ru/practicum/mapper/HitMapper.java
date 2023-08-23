package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.entity.HitEntity;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface HitMapper {
    public HitEntity toHitEntity(IncomingHitDto incomingHitDto);

    public ResponseHitDto toResponseHitDto(HitEntity hitEntity);
}
