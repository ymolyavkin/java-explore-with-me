package ru.practicum.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class ViewStatsResponseDto {
    private String app;
    private String uri;
    private long hits;

    public ViewStatsResponseDto(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
