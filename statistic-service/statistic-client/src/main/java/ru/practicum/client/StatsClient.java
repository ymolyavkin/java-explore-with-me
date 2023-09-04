package ru.practicum.client;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class StatsClient {
    private final String uri="http://localhost:8080";
    private final RestTemplate restTemplate;

    public String saveStats(String item) {
        ResponseEntity<String> response = restTemplate.postForEntity(uri + "/stats", item, String.class);
        return response.getBody();
    }

    public Integer getCount() {
        ResponseEntity<Integer> response = restTemplate.getForEntity(uri + "/", Integer.class);
        return response.getBody();
    }
}
