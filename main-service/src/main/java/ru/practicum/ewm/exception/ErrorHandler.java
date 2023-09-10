package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Throwable e) {
        log.debug("Необработанное исключение. Статус 500 INTERNAL SERVER ERROR {}", e.getMessage(), e);
        return new ApiError(List.of(e.toString()),
                e.getMessage(),
                MESSAGE_INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletParameterException(Throwable e) {
        log.debug("Ошибка на стороне пользователя. Статус 400 BAD_REQUEST {}", e.getMessage(), e);
        return new ApiError(List.of(e.toString()),
                e.getMessage(),
                MESSAGE_BAD_REQUEST,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now());
    }

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

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleConstraintViolation(final RuntimeException e) {
        log.debug("Получен статус 500. Нарушение ограничения БД", e.getMessage(), e);
        return new ApiError(List.of(e.toString()),
                e.getMessage(),
                MESSAGE_INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now());
    }

    @ExceptionHandler({AlreadyExistsException.class, NotAvailableException.class,
            DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final RuntimeException e) {
        log.debug("Получен статус 409 Conflict {}", e.getMessage(), e);
        return new ApiError(List.of(e.toString()),
                e.getMessage(),
                MESSAGE_REASON_DB_CONSTRAINT_VIOLATION,
                HttpStatus.CONFLICT,
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationDateException(ValidationDateException e) {
        log.error("Код ошибки: {}, {}", HttpStatus.BAD_REQUEST, e.getMessage());
        return new ApiError(List.of(e.toString()),
                e.getMessage(),
                MESSAGE_VALIDATION_START_AFTER_END,
                HttpStatus.CONFLICT,
                LocalDateTime.now());
    }
}
