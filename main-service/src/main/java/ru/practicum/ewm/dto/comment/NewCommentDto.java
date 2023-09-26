package ru.practicum.ewm.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {
    @NotBlank(message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 2, max = 2000, message = MESSAGE_VALIDATION_SIZE)
    private String text;
}