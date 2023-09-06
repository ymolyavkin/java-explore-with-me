package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.entity.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByIdIn(List<Long> eventIds);
    Page<Event> findAllByInitiator_Id(Long userId, Pageable pageable);
}
