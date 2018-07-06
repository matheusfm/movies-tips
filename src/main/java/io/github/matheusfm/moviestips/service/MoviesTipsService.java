package io.github.matheusfm.moviestips.service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import io.github.matheusfm.moviestips.model.Genre;
import io.github.matheusfm.moviestips.model.Movie;
import io.github.matheusfm.moviestips.model.Page;
import io.github.matheusfm.moviestips.model.GenreByTemperature;
import io.github.matheusfm.moviestips.service.api.MovieApiService;
import io.github.matheusfm.moviestips.service.api.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe com as regras de negócio do projeto.
 * Responsável por orquestrar as chamadas às APIs de filmes e tempo a fim de retornar as dicas.
 */
@Service
public class MoviesTipsService {
    private final Configuration jsonPathConfiguration;
    private final WeatherApiService weatherApiService;
    private final MovieApiService movieApiService;

    @Autowired
    public MoviesTipsService(WeatherApiService weatherApiService, MovieApiService movieApiService) {
        this.weatherApiService = weatherApiService;
        this.movieApiService = movieApiService;
        this.jsonPathConfiguration = Configuration.builder()
                .jsonProvider(new JacksonJsonProvider())
                .mappingProvider(new JacksonMappingProvider())
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();
    }

    public Page<Movie> getMoviesByCoordinates(Integer page, Double latitude, Double longitude) {
        Object weather = weatherApiService.getTemperatureByCoordinates(latitude, longitude);
        Integer temperature = JsonPath.read(weather, "$.main.temp");
        Page<Movie> moviesPage = movieApiService.getMoviesByGenreId(page, GenreByTemperature.of(temperature));
        translateGenres(moviesPage);
        resolveUrlImages(moviesPage);
        return moviesPage;
    }

    /**
     * Concatena o caminho padrão (que está na configuração da API) na URL das imagens
     *
     * @param moviesPage página de filmes
     */
    private void resolveUrlImages(Page<Movie> moviesPage) {
        String baseUrl = JsonPath.parse(movieApiService.getConfiguration())
                .read("$.images.base_url", String.class)
                .concat("original");

        Optional.of(moviesPage.getResults()).ifPresent(movies -> movies.forEach(movie -> {
            movie.setBackdropPath(baseUrl.concat(movie.getBackdropPath()));
            movie.setPosterPath(baseUrl.concat(movie.getPosterPath()));
        }));
    }

    /**
     * Traduz o ID do gênero para o objeto com o nome do mesmo
     *
     * @param moviesPage página de filmes
     */
    private void translateGenres(Page<Movie> moviesPage) {
        Collection<Genre> genres = JsonPath.using(jsonPathConfiguration)
                .parse(movieApiService.getGenres())
                .read("$.genres", new TypeRef<Collection<Genre>>() {});

        Map<Integer, Genre> genresById = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));

        Optional.of(moviesPage.getResults()).ifPresent(movies -> movies.forEach(movie -> {
            Collection<Genre> gs = movie.getGenreIds().stream().map(genresById::get).collect(Collectors.toSet());
            movie.setGenres(gs);
        }));
    }
}
