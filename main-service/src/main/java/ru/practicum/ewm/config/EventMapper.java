package ru.practicum.ewm.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private ModelMapper mapper;
   /* @Override
    public Unicorn toEntity(UnicornDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Unicorn.class);
    }*/
}
/*
@Component
public class UnicornMapper {

    @Autowired
    private ModelMapper mapper;

    @Override
    public Unicorn toEntity(UnicornDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Unicorn.class);
    }

    @Override
    public UnicornDto toDto(Unicorn entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, UnicornDto.class);
    }
}
 */