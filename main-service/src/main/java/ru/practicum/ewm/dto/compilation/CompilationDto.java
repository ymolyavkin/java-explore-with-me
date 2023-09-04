package ru.practicum.ewm.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.event.EventShortDto;

import javax.validation.constraints.NotNull;

import java.util.List;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private long id;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private String title;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private boolean pinned;
    private List<EventShortDto> events;
}
