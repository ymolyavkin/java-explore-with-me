package ru.practicum.ewm.enums;

import java.util.Optional;

public enum RequestStatus {
    CONFIRMED,
    PENDING,
    CANCELED,
    REJECTED;
    public static Optional<RequestStatus> fromString(String stringStatus) {
        for (RequestStatus status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
