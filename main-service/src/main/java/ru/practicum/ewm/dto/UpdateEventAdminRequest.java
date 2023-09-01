package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.model.Location;
import ru.practicum.validator.Marker;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, groups = Marker.OnUpdate.class)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, groups = Marker.OnUpdate.class)
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventState stateAction;
    @Size(min = 3, max = 120, groups = Marker.OnUpdate.class)
    private String title;
}
