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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByIdIn(List<Long> eventIds);

    Page<Event> findAllByInitiator_Id(Long userId, Pageable pageable);

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

    @Query("SELECT e FROM Event e " +
            "WHERE e.eventsState = 'PUBLISHED' " +
            "AND (COALESCE(:text, NULL) IS NULL OR (LOWER(e.annotation) LIKE LOWER(concat('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(concat('%', :text, '%')))) " +
            "AND (COALESCE(:categories, NULL) IS NULL OR e.category.id IN :categories) " +
            "and (:paid is null or cast(e.paid as text) = :paid) " +
            "AND (COALESCE(:rangeStart, NULL) IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, NULL) IS NULL OR e.eventDate <= :rangeEnd) ")
    List<Event> findAllByPublic(@Param("text") String text,
                                @Param("categories") List<Long> categories,
                                @Param("paid") String paid,
                                @Param("rangeStart") LocalDateTime rangeStart,
                                @Param("rangeEnd") LocalDateTime rangeEnd, PageRequest page);

    @Query("SELECT e FROM Event e " +
            "WHERE e.eventsState = 'PUBLISHED' " +
            "AND (COALESCE(:text, NULL) IS NULL OR (LOWER(e.annotation) LIKE LOWER(concat('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(concat('%', :text, '%')))) " +
            "AND (COALESCE(:categoryIds, NULL) IS NULL OR e.category.id IN :categoryIds) " +
            "AND (COALESCE(:paid, NULL) IS NULL OR e.paid = :paid) " +
            "AND (COALESCE(:rangeStart, NULL) IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, NULL) IS NULL OR e.eventDate <= :rangeEnd) " +
            "AND (:onlyAvailable = false OR e.id IN " +
            "(SELECT r.event.id " +
            "FROM Request r " +
            "WHERE r.status = 'CONFIRMED' " +
            "GROUP BY r.event.id " +
            "HAVING e.participantLimit - count(id) > 0 " +
            "ORDER BY count(r.id))) ")
    List<Event> searchEvent(@Param("text") String text,
                            @Param("categoryIds") List<Long> categoryIds,
                            @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd, @Param("onlyAvailable") Boolean onlyAvailable, Pageable pageable);

    @Query("SELECT MIN(e.publishedOn) FROM Event e WHERE e.id IN :eventsId")
    Optional<LocalDateTime> getStart(@Param("eventsId") Collection<Long> eventsId);

    Set<Event> findByIdIn(List<Long> events);

    List<Event> findAllByCategoryId(Long catId);

    @Query("SELECT e FROM Event e " +
            "WHERE (COALESCE(:users, NULL) IS NULL OR e.initiator.id IN :users) " +
            "AND (COALESCE(:states, NULL) IS NULL OR e.eventsState IN :states) " +
            "AND (COALESCE(:categories, NULL) IS NULL OR e.category.id IN :categories) " +
            "AND (COALESCE(:rangeStart, NULL) IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, NULL) IS NULL OR e.eventDate <= :rangeEnd) ")
    List<Event> findAllAdminByCondition(@Param("users") List<Long> users,
                                        @Param("states") List<EventsState> states,
                                        @Param("categories") List<Long> categories,
                                        @Param("rangeStart") LocalDateTime rangeStart,
                                        @Param("rangeEnd") LocalDateTime rangeEnd, PageRequest page);
}