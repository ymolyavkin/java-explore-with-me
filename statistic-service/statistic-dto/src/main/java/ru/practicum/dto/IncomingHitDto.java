package ru.practicum.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class IncomingHitDto {
    @NotNull(groups = Marker.OnCreate.class)
    private String app;
    @NotNull(groups = Marker.OnCreate.class)
    private String uri;
    @NotNull(groups = Marker.OnCreate.class)
    private String ip;
    private LocalDateTime created;

    @Override
    public String toString() {
        return "IncomingHitDto{" +
                "app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomingHitDto that = (IncomingHitDto) o;
        return Objects.equals(app, that.app) && Objects.equals(uri, that.uri) && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, ip);
    }
}
