package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.entity.Location;
import ru.practicum.ewm.enums.EventsState;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    private String annotation;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    private String description;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private UserShortDto initiator;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Location location;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private boolean paid;
    @PositiveOrZero(message = MESSAGE_VALIDATION_POSITIVE)
    private Integer participantLimit;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventsState state;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    private String title;
    private Long views;
}
