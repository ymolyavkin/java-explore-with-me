package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validator.Marker;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long id;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    @Size(min = 1, max = 50, groups = Marker.OnCreate.class)
    private String name;
}
