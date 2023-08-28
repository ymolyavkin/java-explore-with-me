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
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(final RuntimeException e) {
        log.debug("Ошибка IllegalArgumentException. Статус ошибки 400 Bad Request{}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingParams(MissingServletRequestParameterException e) {
        log.debug("Ошибка MissingServletRequestParameterException. Отсутствует обязательный параметр запросаСтатус ошибки 400 Bad Request{}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(final RuntimeException e) {
        log.debug("Ошибка валидации. Получен статус 400 BAD_REQUEST {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
}
