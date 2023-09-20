package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private Long id;
    @Valid
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = -90, max = 90, message = MESSAGE_VALIDATION_SIZE)
    private Double lat;
    @Valid
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = -180, max = 180, message = MESSAGE_VALIDATION_SIZE)
    private Double lon;
}
