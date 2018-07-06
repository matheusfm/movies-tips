package io.github.matheusfm.moviestips.controller;

import io.github.matheusfm.moviestips.service.MoviesTipsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tips/movies")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MoviesTipsController {
    private final MoviesTipsService moviesTipsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getTipsMoviesByCoordinates(@RequestParam(defaultValue = "1") Integer page, @RequestParam Double latitude, @RequestParam Double longitude) {
        return moviesTipsService.getMoviesByCoordinates(page, latitude, longitude);
    }
}
