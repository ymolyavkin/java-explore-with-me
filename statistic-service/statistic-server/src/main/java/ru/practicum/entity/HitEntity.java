package ru.practicum.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@Table(name = "endpointhit")
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "app", length = 255, nullable = false)
    private String app;
    @Column(name = "uri", length = 255, nullable = false)
    private String uri;
    @Column(name = "ip", length = 15, nullable = false)
    private String ip;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}