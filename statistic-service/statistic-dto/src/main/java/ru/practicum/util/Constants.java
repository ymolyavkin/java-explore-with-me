package ru.practicum.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MESSAGE_VALIDATION_NOT_BLANK = "Данное поле не должно быть пустым";
    public static final String MESSAGE_VALIDATION_SIZE = "Размер поля не удовлетворяет ограничениям";
    public static final String MESSAGE_VALIDATION_POSITIVE = "Ожидается неотрицательное значение";
    public static final String MESSAGE_VALIDATION_START_AFTER_END = "Начало временного интервала позже его окончания";
    public static final String MESSAGE_REASON_ERROR_NOT_FOUND = "The required object was not found.";
    public static final String PAGE_DEFAULT_FROM = "0";
    public static final String PAGE_DEFAULT_SIZE = "10";
}
