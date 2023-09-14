package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.entity.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private long category;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 20, max = 7000, message = MESSAGE_VALIDATION_SIZE)
    private String description;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private LocalDateTime eventDate;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private Location location;
    private Boolean paid = false;
    private Long participantLimit = 0L;
    private Boolean requestModeration = true;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 3, max = 120, message = MESSAGE_VALIDATION_SIZE)
    private String title;
}
