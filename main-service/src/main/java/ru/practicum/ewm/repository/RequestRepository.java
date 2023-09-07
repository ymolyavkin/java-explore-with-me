package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.entity.Request;
import ru.practicum.ewm.enums.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventIdAndEventInitiatorId(Long eventId, Long userId);

    //countAllByEventIdAndStatus
    Long countAllByEventIdAndStatus(Long eventId, RequestStatus status);

    List<Request> findAllByEventIdAndEventInitiatorIdAndIdIn(Long eventId, Long userId, List<Long> requestIds);

}
