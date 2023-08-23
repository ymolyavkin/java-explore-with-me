package ru.practicum.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.validator.Marker;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
}
