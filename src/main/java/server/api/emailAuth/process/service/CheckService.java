package server.api.emailAuth.process.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import server.api.emailAuth.common.util.response.ResponseForm;

import java.net.URI;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CheckService {
    private final RestTemplate restTemplate;

    public boolean isFindEmail(String url, String email){
        return (boolean) getResponseForm(url, email).getBody().getResult();
    }

    private ResponseEntity<ResponseForm> getResponseForm(String url, String email){
        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("email", email)
                .encode()
                .build()
                .toUri();

        return restTemplate.getForEntity(uri, ResponseForm.class);
    }
}
