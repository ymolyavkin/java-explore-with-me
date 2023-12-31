package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 20, max = 2000, message = MESSAGE_VALIDATION_SIZE)
    private String annotation;
    private long category;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 20, max = 7000, message = MESSAGE_VALIDATION_SIZE)
    private String description;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private LocalDateTime eventDate;
    @Valid
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private LocationDto location;
    @NotNull
    private Boolean paid = false;
    @PositiveOrZero
    @NotNull
    private Long participantLimit = 0L;
    @NotNull
    private Boolean requestModeration = true;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 3, max = 120, message = MESSAGE_VALIDATION_SIZE)
    private String title;
}
