package ru.practicum.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.IncomingHitDto;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_START_AFTER_END;

@Service
@Slf4j
public class StatClient extends BaseStatClient {
    @Autowired
    public StatClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> createStat(IncomingHitDto endpointHitDto) {
        log.info("Отправлен get запрос на сервер с данными " + endpointHitDto);
        return post("/hit", endpointHitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException(MESSAGE_VALIDATION_START_AFTER_END);
        }
        Map<String, Object> parameters;
        if (uris != null) {
            parameters = Map.of(
                    "start", URLEncoder.encode(start.toString(), Charset.defaultCharset()),
                    "end", URLEncoder.encode(end.toString(), Charset.defaultCharset()),
                    "uris", String.join(",", uris),
                    "unique", unique
            );
            log.info("Отправлен get запрос на сервер с данными " + parameters);
            return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        } else {
            parameters = Map.of(
                    "start", URLEncoder.encode(start.toString(), Charset.defaultCharset()),
                    "end", URLEncoder.encode(end.toString(), Charset.defaultCharset()),
                    "unique", unique
            );
            log.info("Отправлен get запрос на сервер с данными " + parameters);
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        }
    }

    public ResponseEntity<Object> getView(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
        return get("/view/{eventId}", parameters);
    }
}