package ru.practicum.ewm.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.IncomingHitDto;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.practicum.util.Constants.MESSAGE_VALIDATION_START_AFTER_END;


@Component
@Slf4j
public class Client extends BaseClient {
    @Autowired
    public Client(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public void createStat(HttpServletRequest request) {
        IncomingHitDto incomingHitDto = IncomingHitDto.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .created(LocalDateTime.now())
                .app("ewm-main-service")
                .build();
        log.info("Отправлен post запрос на сервер с данными " + incomingHitDto);
        post("/hit", incomingHitDto);
    }

    /*
    IncomingHitDto{app='ewm-main-service', uri='/events/294', ip='129.254.84.176', created=null}
     */
    // public Long getView(Long eventId) {
    public String getView(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
        String responseBody = (Objects.requireNonNullElse(get("/view/{eventId}", parameters).getBody(), 0L)).toString();
        // return Long.parseLong(responseBody);
        return responseBody;
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

    public ResponseEntity<Object> getStatisticsOnHits(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", String.join(",", uris),
                "unique", unique
        );
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
        log.info("Отправлен get запрос на сервер с данными " + parameters);
        return get("/viewstats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> getStatisticsByHits(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", String.join(",", uris),
                "unique", unique
        );
        log.info("Получен запрос на получение статистики по посещениям с {} по {}", start, end);
        log.info("Отправлен get запрос на сервер с данными " + parameters);
        return get("/custom?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
    public ResponseEntity<Object> getPathVariableClient(String client, Long id) {
        Map<String, Object> parameters = Map.of(
                "pathVariable", "test from Client");
        return get("/custom?brand=2022-01-06%2013%3A30%3A38&limit=20&price=20000&sort=asc", parameters);
    }
}