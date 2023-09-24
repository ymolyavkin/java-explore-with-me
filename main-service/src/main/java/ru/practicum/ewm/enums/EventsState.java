package ru.practicum.ewm.enums;

import java.util.Optional;

public enum EventsState {
    PENDING,
    CANCELED,
    PUBLISHED;

    public static Optional<EventsState> fromString(String stringState) {
        for (EventsState eventsState : values()) {
            if (eventsState.name().equalsIgnoreCase(stringState)) {
                return Optional.of(eventsState);
            }
        }
        return Optional.empty();
    }
}
