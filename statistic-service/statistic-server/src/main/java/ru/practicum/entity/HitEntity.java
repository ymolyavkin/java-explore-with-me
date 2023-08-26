package ru.practicum.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME_PATTERN;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "endpointhit", schema = "public")
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
    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime created;
}