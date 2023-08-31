package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class,
            ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingParams(RuntimeException e) {
        log.debug("Ошибка RuntimeException. Отсутствует обязательный параметр запроса. Статус ошибки 400 Bad Request{}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowable(final RuntimeException e) {
        log.debug("Необработанное исключение. Получен статус 500 INTERNAL_SERVER_ERROR {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
}
