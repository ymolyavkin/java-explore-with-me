package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private Long id;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Min(value = -90, message = MESSAGE_VALIDATION_SIZE)
    @Max(value = 90, message = MESSAGE_VALIDATION_SIZE)
    private Double lat;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Min(value = -180, message = MESSAGE_VALIDATION_SIZE)
    @Max(value = 180, message = MESSAGE_VALIDATION_SIZE)
    private Double lon;
}
