package ru.practicum.ewm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    @Override
    public String toString() {
        return "ApiError{" +
                ", message='" + message + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
