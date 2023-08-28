package ru.practicum.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MESSAGE_VALIDATION_NOT_BLANK = "Данное поле не должно быть пустым";
    public static final String MESSAGE_VALIDATION_START_AFTER_END = "Начало временного интрвала позже его окончания";
}
