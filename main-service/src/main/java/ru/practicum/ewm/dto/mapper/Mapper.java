package ru.practicum.ewm.dto.mapper;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.entity.Request;

@UtilityClass
public class Mapper {
    public static ParticipationRequestDto mapToParticipationRequestDto(ModelMapper mapper, Request request) {
        if (mapper.getTypeMap(Request.class, ParticipationRequestDto.class) == null) {
            mapper.createTypeMap(Request.class, ParticipationRequestDto.class);
        }

        TypeMap<Request, ParticipationRequestDto> eventMapper = mapper.getTypeMap(Request.class, ParticipationRequestDto.class);

        eventMapper.addMapping(src -> src.getEvent().getId(), ParticipationRequestDto::setEvent);
        eventMapper.addMapping(src -> src.getRequester().getId(), ParticipationRequestDto::setRequester);

        return mapper.map(request, ParticipationRequestDto.class);
    }

}
