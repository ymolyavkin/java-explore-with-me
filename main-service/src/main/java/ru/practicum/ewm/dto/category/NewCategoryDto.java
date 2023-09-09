package ru.practicum.ewm.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
import static ru.practicum.util.Constants.MESSAGE_VALIDATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 1, max = 50, groups = {Marker.OnCreate.class, Marker.OnUpdate.class}, message = MESSAGE_VALIDATION_SIZE)
    private String name;
}