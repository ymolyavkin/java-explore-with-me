package ru.practicum.ewm.enums;

import java.util.Optional;

public enum EventState {
    PUBLISH_EVENT,
    REJECT_EVENT;
    public static Optional<EventState> fromString(String stringState) {
        for (ru.practicum.ewm.enums.EventState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return java.util.Optional.of(state);
            }
        }
        return java.util.Optional.empty();
    }
}
