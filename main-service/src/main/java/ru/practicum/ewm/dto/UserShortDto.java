package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private long id;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String name;
}
