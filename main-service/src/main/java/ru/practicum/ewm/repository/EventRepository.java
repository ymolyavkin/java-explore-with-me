package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByIdIn(List<Long> eventIds);
    Page<Event> findAllByInitiator_Id(Long userId, Pageable pageable);
   /* @Query("select e" +
            " from Event e" +
            " where e.initiator.id = :initiatorId" +
            " and e.id = :eventId")
    Optional<Event> findEventByInitiatorIdAndEventId(Long initiatorId, Long eventId);*/
   Optional<Event> findByInitiator_IdAndAndId(Long initiatorId, Long eventId);
}
