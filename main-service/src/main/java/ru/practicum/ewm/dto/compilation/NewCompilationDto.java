package ru.practicum.ewm.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;

    @NotBlank(groups = {Marker.OnCreate.class}, message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(groups = {Marker.OnCreate.class}, min = 1, max = 50, message = MESSAGE_VALIDATION_SIZE)
    private String title;
}
