package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class BookingClient extends BaseBookingClient {
    private static final String API_PREFIX = "/stats";
    //private static final String API_PREFIX = "/bookings";
//http://localhost:9090/stats?start=2020-05-05%2000:00:00&end=2035-05-05%2000:00:00&unique=false
    @Autowired
    public BookingClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getBookings(long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", "state.name()",
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }
    public ResponseEntity<Object> getStats() {
        return get("/?start=2020-05-05%2000:00:00&end=2035-05-05%2000:00:00&unique=false");
    }
    public ResponseEntity<Object> getTestBooking(String booking) {
        return get("/test?pathVariable=startFromBookingClient");
    }
}
