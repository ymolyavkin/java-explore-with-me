package ru.practicum.ewm.enums;

import java.util.Optional;

public enum ChangeEventState {
    SEND_TO_REVIEW,
    CANCEL_REVIEW;
    public static Optional<ChangeEventState> fromString(String stringState) {
        for (ChangeEventState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
