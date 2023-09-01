package ru.practicum.ewm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    @NotNull(message = MESSAGE_VALIDATION_NOT_BLANK)
    private long id;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    private String name;
}
