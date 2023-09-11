package ru.practicum.dto;

import lombok.*;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_NOT_BLANK;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class IncomingHitDto {
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String app;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
    private String uri;
    @NotBlank(groups = Marker.OnCreate.class, message = MESSAGE_VALIDATION_NOT_BLANK)
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
