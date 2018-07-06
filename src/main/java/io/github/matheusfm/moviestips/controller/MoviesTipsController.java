package io.github.matheusfm.moviestips.controller;

import io.github.matheusfm.moviestips.model.Movie;
import io.github.matheusfm.moviestips.model.Page;
import io.github.matheusfm.moviestips.service.MoviesTipsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "Movies Tips API")
public class MoviesTipsController {
    private final MoviesTipsService moviesTipsService;

    @ApiOperation(value = "Get movies based on coordinates")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Movie> getTipsMoviesByCoordinates(@RequestParam(defaultValue = "1") Integer page,
                                                  @ApiParam(example = "-20.63") @RequestParam Double latitude,
                                                  @ApiParam(example = "-49.65")@RequestParam Double longitude) {
        return moviesTipsService.getMoviesByCoordinates(page, latitude, longitude);
    }
}
