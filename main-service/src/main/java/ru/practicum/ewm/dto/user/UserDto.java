package ru.practicum.ewm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 2, max = 250, message = MESSAGE_VALIDATION_SIZE)
    private String name;
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 6, max = 254, message = MESSAGE_VALIDATION_SIZE)
    private String email;
}