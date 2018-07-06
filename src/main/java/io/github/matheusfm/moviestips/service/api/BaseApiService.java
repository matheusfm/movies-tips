package io.github.matheusfm.moviestips.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

/**
 * Classe com os métodos comuns para chamadas às APIs
 * <p>
 * Todas as requests são GET
 * Todas as requests têm query params
 * Nenhuma request tem path params
 * Todas as responses são JSON
 */
public abstract class BaseApiService {
    private final Logger logger = LoggerFactory.getLogger(BaseApiService.class);

    private final RestTemplate restTemplate;
    private String baseUrl;

    protected BaseApiService(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    /**
     * Método para adicionar as query params default de cada API
     *
     * @param queryParams query params
     */
    abstract void addDefaultQueryParams(MultiValueMap<String, String> queryParams);

    final Object execute(String path) {
        return execute(path, new LinkedMultiValueMap<>(), Object.class);
    }

    final Object execute(String path, MultiValueMap<String, String> queryParams) {
        return execute(path, queryParams, Object.class);
    }

    final <T> T execute(String path, MultiValueMap<String, String> queryParams, ParameterizedTypeReference<T> type) {
        addDefaultQueryParams(Optional.ofNullable(queryParams).orElse(new LinkedMultiValueMap<>()));

        ResponseEntity<T> responseEntity = restTemplate.exchange(getUri(path, queryParams),
                HttpMethod.GET,
                buildHttpEntity(),
                type);

        logger.info("Response status: {}", responseEntity.getStatusCodeValue());

        return responseEntity.getBody();
    }

    final <T> T execute(String path, MultiValueMap<String, String> queryParams, Class<T> clazz) {
        addDefaultQueryParams(Optional.ofNullable(queryParams).orElse(new LinkedMultiValueMap<>()));

        ResponseEntity<T> responseEntity = restTemplate.exchange(getUri(path, queryParams),
                HttpMethod.GET,
                buildHttpEntity(),
                clazz);

        logger.info("Response status: {}", responseEntity.getStatusCodeValue());

        return responseEntity.getBody();
    }

    private URI getUri(String path, MultiValueMap<String, String> queryParams) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParams(queryParams)
                .path(path)
                .build()
                .toUri();

        logger.info("Request: {} {}", HttpMethod.GET, uri);
        return uri;
    }

    private HttpEntity<?> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }
}
