package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotNull;

import java.util.List;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private long id;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String title;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private boolean pinned;
    private List<EventShortDto> events;
}
