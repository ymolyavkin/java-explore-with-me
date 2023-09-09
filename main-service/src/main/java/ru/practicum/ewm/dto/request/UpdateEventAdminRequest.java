package ru.practicum.ewm.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.enums.StateAction;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, message = MESSAGE_VALIDATION_SIZE)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = MESSAGE_VALIDATION_SIZE)
    private String description;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero(message = MESSAGE_VALIDATION_POSITIVE)
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120, message = MESSAGE_VALIDATION_SIZE)
    private String title;
}
