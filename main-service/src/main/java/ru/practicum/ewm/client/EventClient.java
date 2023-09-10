package ru.practicum.ewm.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseStatisticClient;

import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@Service
public class EventClient extends BaseStatisticClient {
    /*public EventClient(RestTemplate rest) {
        super(rest);
    }*/
    private static final String API_PREFIX = "/stats";
    @Autowired
    public EventClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }
    public ResponseEntity<Object> getAllEvents(LocalDateTime rangeStart,
                                                      LocalDateTime rangeEnd,
                                                      int from,
                                                      int size) {
        /*Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );*/
       // rangeStart = rangeStart.plusYears(1);
        HashMap<String, Object> parameters = new HashMap<>();
        StringBuilder path = new StringBuilder("?start={rangeStart}&end={rangeEnd}");

            parameters.put("start", rangeStart);
            path.append("&start={rangeStart}");

        if (rangeEnd == null) {
            rangeEnd=rangeStart.plusMonths(1);
        }
        if (rangeEnd != null) {
            parameters.put("end", rangeEnd);
            path.append("&end={rangeEnd}");
        }
//        parameters.put("from", from);
//        parameters.put("size", size);



      /*  if (text != null) {
            parameters.put("text", text);
            path.append("&text={text}");
        }
        if (categories != null) {
            for (Integer id : categories) {
                parameters.put("categories" + id, id);
                path.append("&categories={categories").append(id).append("}");
            }
        }
        if (paid != null) {
            parameters.put("paid", paid);
            path.append("&paid={paid}");
        }*/
        if (rangeStart != null) {
            parameters.put("start", rangeStart);
            path.append("&start={rangeStart}");
            path.append("&end={rangeStart}");
        }
        if (rangeEnd == null) {
            rangeEnd=rangeStart.plusMonths(1);
        }
        if (rangeEnd != null) {
            parameters.put("end", rangeEnd);
            path.append("&end={rangeEnd}");
        }
        /*if (onlyAvailable != null) {
            parameters.put("onlyAvailable", onlyAvailable);
            path.append("&onlyAvailable={onlyAvailable}");
        }
        if (sort != null) {
            parameters.put("sort", sort);
            path.append("&sort={sort}");
        }*/


        log.info("Запрос на сервер статистики с id = {} ");
        //return get(path.toString(), parameters);
        //return get("/", parameters);
       // return get("?start={rangeStart}&end={rangeEnd}", parameters);
        return get("?start={start}&end={end}", parameters);
    }
}
//?start=2020-05-05%2000:00:00&end=2035-05-05%2000:00:00