package io.github.matheusfm.moviestips.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Classe resposável pela comunicação com a API OpenWeatherMap
 */
@Service
public class WeatherApiService extends BaseApiService {
    /**
     * Variável de ambiente API_WEATHER_APIKEY
     */
    private final String apiKey;
    private final String lang;
    private final String units;

    @Autowired
    public WeatherApiService(RestTemplate restTemplate,
                             @Value("${api.weather.apiKey}") String apiKey,
                             @Value("${api.weather.lang}") String lang,
                             @Value("${api.weather.units}") String units,
                             @Value("${api.weather.baseUrl}") String baseUrl) {
        super(restTemplate, baseUrl);
        this.apiKey = apiKey;
        this.lang = lang;
        this.units = units;
    }

    public Object getTemperatureByCoordinates(Double latitude, Double longitude) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lat", latitude.toString());
        queryParams.add("lon", longitude.toString());
        return execute("/weather", queryParams);
    }

    @Override
    void addDefaultQueryParams(MultiValueMap<String, String> queryParams) {
        queryParams.add("appid", apiKey);
        queryParams.add("lang", lang);
        queryParams.add("units", units);
    }
}
