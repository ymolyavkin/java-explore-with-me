package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.entity.Event;
import ru.practicum.ewm.enums.EventsState;

import java.time.LocalDateTime;
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

    @Query("SELECT e " +
            "FROM Event AS e " +
            "JOIN FETCH e.initiator " +
            "JOIN FETCH e.category " +
            "WHERE e.eventDate > :rangeStart " +
            "AND (e.category.id IN :categories OR :categories IS NULL) " +
            "AND (e.initiator.id IN :users OR :users IS NULL) " +
            "AND (e.eventsState IN :states OR :states IS NULL)"
    )
//    Page<Event> findAllForAdmin(List<Long> users, List<EventsState> states, List<Long> categories,
//                                LocalDateTime rangeStart, PageRequest pageable);
    List<Event> findAllForAdmin(List<Long> users, List<EventsState> states, List<Long> categories,
                                LocalDateTime rangeStart, PageRequest pageable);

    @Query("select e from Event e " +
            "where e.eventsState = 'PUBLISHED' " +
            "and (coalesce(:text, null) is null or (lower(e.annotation) like lower(concat('%', :text, '%')) or lower(e.description) like lower(concat('%', :text, '%')))) " +
            "and (coalesce(:categoryIds, null) is null or e.category.id in :categoryIds) " +
            "and (coalesce(:paid, null) is null or e.paid = :paid) " +
            "and e.eventDate >= :rangeStart " +
            "and (coalesce(:rangeEnd, null) is null or e.eventDate <= :rangeEnd) " +
            "and (:onlyAvailable = false or e.id in " +
            "(select r.event.id " +
            "from Request r " +
            "where r.status = 'CONFIRMED' " +
            "group by r.event.id " +
            "having e.participantLimit - count(r.id) > 0 " +
            "order by count(r.id))) ")
    List<Event> findAllPublic(@Param("text") String text, @Param("categoryIds") List<Long> categoryIds,
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              @Param("rangeEnd") LocalDateTime rangeEnd, @Param("onlyAvailable") Boolean onlyAvailable,
                              Pageable pageable);

    /*  @Query("select e from Event e " +
               "where e.eventsState = 'PUBLISHED' " +
               "and (:text is null or (lower(e.annotation) like lower(concat('%', :text, '%')) or lower(e.description) like lower(concat('%', :text, '%')))) " +
               "and (:categoryIds is null or e.category.id in :categoryIds) " +
               "and (:paid is null or e.paid = :paid) " +
               "and e.eventDate >= :rangeStart " +
               "and (:rangeEnd is null or e.eventDate <= :rangeEnd) " +
               "and (:onlyAvailable = false or e.id in " +
               "(select r.event.id " +
               "from Request r " +
               "where r.status = 'CONFIRMED' " +
               "group by r.event.id " +
               "having e.participantLimit - count(r.id) > 0 " +
               "order by count(r.id))) ")*/
    @Query("select e from Event e " +
            "where e.eventsState = 'PUBLISHED' " +
            "and (:text is null or (lower(e.annotation) like lower(concat('%', :text, '%')) or lower(e.description) like lower(concat('%', :text, '%')))) " +
            "and (:categoryIds is null or e.category.id in :categoryIds) " +
            "and (:paid is null or cast(e.paid as text) = :paid) " +
            "and e.eventDate >= :rangeStart " +
            "and (:rangeEnd is null or e.eventDate <= :rangeEnd) " +
            "and (:onlyAvailable = false or e.id in " +
            "(select r.event.id " +
            "from Request r " +
            "where r.status = 'CONFIRMED' " +
            "group by r.event.id " +
            "having e.participantLimit - count(r.id) > 0 " +
            "order by count(r.id))) "
    )

    //

    List<Event> findAllPublicByCondition(
            @Param("text") String text, @Param("categoryIds") List<Long> categoryIds,
            @Param("paid") String paid, @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd, @Param("onlyAvailable") Boolean onlyAvailable, Pageable pageable);

    @Query("select e from Event e " +
            "where e.eventsState = 'PUBLISHED' " +
            "and (:text is null or (lower(e.annotation) like lower(concat('%', :text, '%')) or lower(e.description) like lower(concat('%', :text, '%')))) " +
            "and (:categoryIds is null or e.category.id in :categoryIds) " +
            "and (:paid is null or cast(e.paid as text) = :paid) "
    )
    List<Event> findAllPublicByConditionTest(@Param("text") String text, @Param("categoryIds") List<Long> categoryIds, @Param("paid") String paid, Pageable pageable);
}