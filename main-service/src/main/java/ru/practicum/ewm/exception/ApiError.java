package ru.practicum.ewm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ApiError {
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}
