package ru.practicum.ewm.enums;

import java.util.Optional;

public enum State {
    PENDING,
    CANCELED,
    PUBLISHED;

    public static Optional<State> fromString(String stringState) {
        for (State state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
