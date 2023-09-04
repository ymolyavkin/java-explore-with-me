package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.MESSAGE_REASON_ERROR_NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final RuntimeException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ApiError(List.of(e.toString()),
                e.getMessage(),
                MESSAGE_REASON_ERROR_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                LocalDateTime.now());
    }
}
