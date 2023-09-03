package ru.practicum.ewm.enums;

import java.util.Optional;

public enum SortingOption {
    EVENT_DATE,
    VIEWS;
    public static Optional<SortingOption> fromString(String stringSortingOptions) {
        for (SortingOption sortingOption : values()) {
            if (sortingOption.name().equalsIgnoreCase(stringSortingOptions)) {
                return java.util.Optional.of(sortingOption);
            }
        }
        return java.util.Optional.empty();
    }
}
