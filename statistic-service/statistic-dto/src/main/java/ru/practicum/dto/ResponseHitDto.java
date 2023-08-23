package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
public class ResponseHitDto {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHitDto that = (ResponseHitDto) o;
        return id == that.id && Objects.equals(app, that.app) && Objects.equals(uri, that.uri) && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, uri, ip);
    }

    @Override
    public String toString() {
        return "ResponseHitDto{" +
                "id=" + id +
                ", app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }
}
