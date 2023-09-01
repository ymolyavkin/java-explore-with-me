package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.Location;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String annotation;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private CategoryDto category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String Description;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private LocalDateTime eventDate;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private UserShortDto initiator;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private Location location;
    @NotNull(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String title;
    private Long views;
}
