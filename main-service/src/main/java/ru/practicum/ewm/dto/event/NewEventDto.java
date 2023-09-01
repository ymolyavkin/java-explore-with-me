package ru.practicum.ewm.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

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
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private LocalDateTime eventDate;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 3, max = 120, message = MESSAGE_VALIDATION_SIZE)
    private String title;
}
