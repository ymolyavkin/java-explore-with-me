package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.LocationDto;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.enums.EventsState;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LocationDto location;
    private boolean paid;
    private Long participantLimit;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventsState state;
    private String title;
    private Long views;
}
