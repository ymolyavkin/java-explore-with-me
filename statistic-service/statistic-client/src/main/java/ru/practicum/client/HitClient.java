package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.IncomingHitDto;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class HitClient extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public HitClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addHit(IncomingHitDto incomingHitDto) {
        log.info("Получен запрос на сохранение информации о том что эндпойнт запрашивали");
        return post("/hit", incomingHitDto);
    }

    public ResponseEntity<Object> getStatisticsOnHits(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", String.join(",", uris),
                "unique", unique
        );
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
        return get("?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}
