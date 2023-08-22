package ru.practicum.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Hit {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hit hit = (Hit) o;
        return id == hit.id && Objects.equals(app, hit.app) && Objects.equals(uri, hit.uri) && Objects.equals(ip, hit.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, uri, ip);
    }

    @Override
    public String toString() {
        return "Hit{" +
                "id=" + id +
                ", app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }
}
