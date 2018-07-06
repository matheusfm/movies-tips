package io.github.matheusfm.moviestips.service.api;

import io.github.matheusfm.moviestips.model.Movie;
import io.github.matheusfm.moviestips.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Classe resposável pela comunicação com a API TheMovieDB
 */
@Service
public class MovieApiService extends BaseApiService {
    private final String apiKey;
    private final String lang;

    @Autowired
    public MovieApiService(RestTemplate restTemplate,
                           @Value("${api.movie.baseUrl}") String baseUrl,
                           @Value("${api.movie.apiKey}") String apiKey,
                           @Value("${api.movie.lang}") String lang) {
        super(restTemplate, baseUrl);
        this.apiKey = apiKey;
        this.lang = lang;
    }

    public Page<Movie> getMoviesByGenreId(Integer page, Integer genreId) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page.toString());
        queryParams.add("with_genres", genreId.toString());
        return execute("/discover/movie", queryParams, new ParameterizedTypeReference<Page<Movie>>() {});
    }

    public Object getGenres() {
        return execute("/genre/movie/list");
    }

    public Object getConfiguration() {
        return execute("/configuration");
    }

    @Override
    void addDefaultQueryParams(MultiValueMap<String, String> queryParams) {
        queryParams.add("api_key", apiKey);
        queryParams.add("language", lang);
        queryParams.add("include_adult", Boolean.FALSE.toString());
        queryParams.add("include_video", Boolean.FALSE.toString());
        queryParams.add("sort_by", "popularity.desc");
    }
}
