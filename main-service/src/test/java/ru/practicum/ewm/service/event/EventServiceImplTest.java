package ru.practicum.ewm.service.event;

import org.junit.jupiter.api.Test;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.enums.RequestStatus;

import java.util.List;

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
        requestToStatusConfirmed.setRequestIds(List.of(1L, 2L));
        requestToStatusConfirmed.setStatus(CONFIRMED);

        EventRequestStatusUpdateRequest requestToStatusRejected = new EventRequestStatusUpdateRequest();
        requestToStatusConfirmed.setRequestIds(List.of(3L, 4L));
        requestToStatusConfirmed.setStatus(REJECTED);
    }
}
