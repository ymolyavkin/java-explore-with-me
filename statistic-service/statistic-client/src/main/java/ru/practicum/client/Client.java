package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.IncomingHitDto;
import ru.practicum.dto.ViewStatsResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.practicum.util.Constants.DATE_TIME_PATTERN;

@Component
@Slf4j
public class Client extends BaseClient {
    @Value("${main-app.name}")
    String mainApplicationName;
    @Value("${statistic-server.url}")
    String serverUrl;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

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
                .app(mainApplicationName)
                .build();
        log.info("Отправлен post запрос на сервер с данными {}", incomingHitDto);
        post("/hit", incomingHitDto);
    }

    public List<ViewStatsResponseDto> getStats(LocalDateTime start, LocalDateTime end,
                                               List<String> uris, boolean unique) {
        Map<String, Object> parameters = new HashMap<>(Map.of(
                "start", start.format(formatter),
                "end", end.format(formatter),
                "unique", unique));

        if (uris != null && !uris.isEmpty()) {
            parameters.put("uris", String.join(",", uris));
        }

        ViewStatsResponseDto[] response = rest.getForObject(
                serverUrl.concat("/stats?start={start}&end={end}&uris={uris}&unique={unique}"),
                ViewStatsResponseDto[].class, parameters);

        return Objects.isNull(response)
                ? List.of()
                : List.of(response);
    }

    public Long getView(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
        String responseBody = (Objects.requireNonNullElse(get("/view/{eventId}", parameters).getBody(), 0L)).toString();
        return Long.parseLong(responseBody);
    }
}