package ru.practicum.ewm.entity;

import lombok.Data;
import lombok.ToString;
import ru.practicum.ewm.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @ToString.Exclude
    private User requester;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private State status;
}
