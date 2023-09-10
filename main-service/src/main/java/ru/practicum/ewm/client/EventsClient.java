package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.dto.IncomingHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.Constants.FORMATTER;

@Service
public class EventsClient extends BaseCommonClient {

    @Autowired
    public EventsClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createHit(IncomingHitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String path = getStatsPath(uris);
        Map<String, Object> parameters = getStatsParameters(start, end, uris, unique);
        return get(path, parameters);
    }

    private String getStatsPath(List<String> uris) {
        if (uris == null) {
            return "/stats?start={start}&end={end}&unique={unique}";
        } else {
            return "/stats?start={start}&end={end}&unique={unique}&uris={uris}";
        }
    }

    private Map<String, Object> getStatsParameters(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (uris == null) {
            return Map.of(
                    "start", start.format(FORMATTER),
                    "end", end.format(FORMATTER),
                    "unique", unique
            );
        } else {
            return Map.of(
                    "start", start.format(FORMATTER),
                    "end", end.format(FORMATTER),
                    "unique", unique,
                    "uris", String.join(", ", uris)
            );
        }
    }

}