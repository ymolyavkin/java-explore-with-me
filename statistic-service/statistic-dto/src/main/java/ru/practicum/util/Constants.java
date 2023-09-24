package ru.practicum.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constants {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final String FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    public static final String MESSAGE_VALIDATION_NOT_BLANK = "Данное поле не должно быть пустым";
    public static final String MESSAGE_VALIDATION_SIZE = "Размер поля не удовлетворяет ограничениям";
    public static final String MESSAGE_VALIDATION_POSITIVE = "Ожидается неотрицательное значение";
    public static final String MESSAGE_VALIDATION_START_AFTER_END = "Начало временного интервала позже его окончания";
    public static final String MESSAGE_DATE_NOT_VALID = "Недопустимый временной промежуток";
    public static final String MESSAGE_REASON_ERROR_NOT_FOUND = "Запрошенный объект не найден";
    public static final String MESSAGE_REASON_DB_CONSTRAINT_VIOLATION = "Нарушение ограничения БД";
    public static final String MESSAGE_INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";
    public static final String MESSAGE_BAD_REQUEST = "Ошибка на стороне пользователя";
    public static final String MESSAGE_EVENT_IS_NOT_PUBLISHED = "Данное событие не опубликовано";
    public static final String MESSAGE_USER_IS_NOT_AUTHOR = "Комментарий не принадлежит данному пользователю";
    public static final String PAGE_DEFAULT_FROM = "0";
    public static final String PAGE_DEFAULT_SIZE = "10";
}
