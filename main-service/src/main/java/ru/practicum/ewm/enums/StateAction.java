package ru.practicum.ewm.enums;

import java.util.Optional;

public enum StateAction {
    PUBLISH_EVENT,
    REJECT_EVENT;
    public static Optional<StateAction> fromString(String stringState) {
        for (StateAction state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return java.util.Optional.of(state);
            }
        }
        return java.util.Optional.empty();
    }
}