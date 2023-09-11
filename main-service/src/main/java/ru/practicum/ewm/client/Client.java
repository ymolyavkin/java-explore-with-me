package ru.practicum.ewm.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.IncomingHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


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
        log.info("Отправлен get запрос на сервер с данными " + incomingHitDto);
        post("/hit", incomingHitDto);
    }
/*
IncomingHitDto{app='ewm-main-service', uri='/events/294', ip='129.254.84.176', created=null}
 */
    public Long getView(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
        String responseBody = (Objects.requireNonNullElse(get("/view/{eventId}", parameters).getBody(), 0L)).toString();
        return Long.parseLong(responseBody);
    }
}