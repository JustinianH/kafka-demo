package com.kafkademo.client;

import com.kafkademo.models.ChangesInMe;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class FilterServiceClient {

    public List<ChangesInMe> filterChangesInMe(List<ChangesInMe> changesInMeList, Integer filterNumber) {

        String serviceUrl = "http://localhost:7071/api/HttpTrigger2";
        Map payload = Map.of("changes", changesInMeList, "filterNumber", filterNumber);

        WebClient client = WebClient.builder()
                .baseUrl(serviceUrl)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        List<ChangesInMe> results = client
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ChangesInMe>>() {})
                .block();

        return results;
    }
}
