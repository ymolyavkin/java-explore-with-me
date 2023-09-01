package ru.practicum.ewm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_POSITIVE;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, message = MESSAGE_VALIDATION_SIZE)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = MESSAGE_VALIDATION_SIZE)
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero(message = MESSAGE_VALIDATION_POSITIVE)
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventState stateAction;
    @Size(min = 3, max = 120, message = MESSAGE_VALIDATION_SIZE)
    private String title;
}
