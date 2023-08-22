package ru.practicum.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "endpointhit", schema = "public")
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(name = "app", length = 255, nullable = false)
    private String app;
    @NonNull
    @Column(name = "uri", length = 255, nullable = false)
    private String uri;
    @NonNull
    @Column(name = "ip", length = 15, nullable = false)
    private String ip;
    @NonNull
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}