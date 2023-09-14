package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.dto.category.CategoryDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_PATTERN;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    private String annotation;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private CategoryDto category;
    private Long confirmedRequests;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private UserShortDto initiator;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private boolean paid;
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private String title;
    private Long views;
}
