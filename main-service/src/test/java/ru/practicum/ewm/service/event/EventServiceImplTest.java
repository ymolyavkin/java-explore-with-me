package ru.practicum.ewm.service.event;

import org.junit.jupiter.api.Test;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;

import java.util.Set;

import static ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest.Status.CONFIRMED;
import static ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest.Status.REJECTED;

class EventServiceImplTest {
    private EventServiceImpl eventService;

    @Test
    void changeStatusRequestsTest() {
        Long userId = 1L;
        Long eventId = 1L;
        Long userId2 = 2L;
        Long eventId2 = 2L;
        EventRequestStatusUpdateRequest requestToStatusConfirmed = new EventRequestStatusUpdateRequest();
        requestToStatusConfirmed.setRequestIds(Set.of(1L, 2L));
        requestToStatusConfirmed.setStatus(CONFIRMED);

        EventRequestStatusUpdateRequest requestToStatusRejected = new EventRequestStatusUpdateRequest();
        requestToStatusConfirmed.setRequestIds(Set.of(1L, 2L));
        requestToStatusConfirmed.setStatus(REJECTED);
    }
}
