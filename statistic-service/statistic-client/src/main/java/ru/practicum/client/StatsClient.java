package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.IncomingHitDto;
//import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class StatsClient {
    private final String uri="http://localhost:8080";
    private final RestTemplate restTemplate;
    //private final WebClient client;

//    public String saveStats(String item) {
//        ResponseEntity<String> response = restTemplate.postForEntity(uri + "/stats", item, String.class);
//        return response.getBody();
//    }
//
//    public Integer getCount() {
//        ResponseEntity<Integer> response = restTemplate.getForEntity(uri + "/", Integer.class);
//        return response.getBody();
//    }
public String saveStats(IncomingHitDto incomingHitDto) {
    ResponseEntity<String> response = restTemplate.postForEntity(uri + "/stats", incomingHitDto, String.class);
    return response.getBody();
}

    public Integer getCount() {
        ResponseEntity<Integer> response = restTemplate.getForEntity(uri + "/", Integer.class);
        return response.getBody();
    }
//    public ResponseEntity<List<ViewStatsDto>> getStats(String start, String end, List<String> uris, Boolean unique) {
//        return this.client.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/stats")
//                        .queryParam("start", start)
//                        .queryParam("end", end)
//                        .queryParam("uris", uris)
//                        .queryParam("unique", unique)
//                        .build())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .toEntityList(ViewStatsDto.class)
//                .doOnNext(c -> log.info("Get stats with param: start date {}, end date {}, uris {}, unique {}",
//                        start, end, uris, unique))
//                .block();
//    }
}
