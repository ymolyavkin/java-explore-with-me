package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private long id;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String annotation;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private CategoryDto category;
    private long confirmedRequests;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private LocalDateTime eventDate;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private UserShortDto initiator;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private boolean paid;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String title;
    private long views;
}
