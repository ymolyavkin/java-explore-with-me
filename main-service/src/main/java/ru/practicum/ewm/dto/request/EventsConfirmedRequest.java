package ru.practicum.ewm.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class EventsConfirmedRequest {
    private long eventId;
    private long countConfirmedRequests;

    public EventsConfirmedRequest(long eventId, long countConfirmedRequests) {
        this.eventId = eventId;
        this.countConfirmedRequests = countConfirmedRequests;
    }
}
