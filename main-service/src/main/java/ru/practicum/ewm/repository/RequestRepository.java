package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.request.EventsConfirmedRequest;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.entity.Request;
import ru.practicum.ewm.entity.User;
import ru.practicum.ewm.enums.RequestStatus;

import java.util.Collection;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventIdAndEventInitiatorId(Long eventId, Long userId);

    @Query("SELECT count(r.id) FROM Request AS r " +
            "WHERE r.event.id in :eventId " +
            "AND r.status = :status")
    Long countAllByEventIdAndStatus(Long eventId, RequestStatus status);

    List<Request> findAllByEventIdAndEventInitiatorIdAndIdIn(Long eventId, Long userId, Collection<Long> requestIds);

    List<Request> findByRequesterId(Long userId);

    boolean existsByRequesterAndEvent(User user, Event event);

    List<Request> findAllByStatusAndEventIdIn(RequestStatus status, List<Long> eventIds);

    List<Request> findAllByStatusAndEventIdIn(EventRequestStatusUpdateRequest.Status status, List<Long> eventIds);

    @Query("SELECT count(r.id) FROM Request AS r " +
            "WHERE r.event.id in :eventId " +
            "AND r.status = 'CONFIRMED' ")
    Long findConfirmedRequests(Long eventId);

    @Query("SELECT new ru.practicum.ewm.dto.request.EventsConfirmedRequest(r.event.id, COUNT( r.id)) " +
            "FROM Request r " +
            "WHERE r.event in :events " +
            "AND r.status = 'CONFIRMED' " +
            "GROUP BY r.event.id")
    List<EventsConfirmedRequest> getCountConfirmedRequests(Collection<Event> events);
}
