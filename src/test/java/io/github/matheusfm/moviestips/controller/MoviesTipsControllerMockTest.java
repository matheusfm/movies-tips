package io.github.matheusfm.moviestips.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.matheusfm.moviestips.service.api.WeatherApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MoviesTipsControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherApiService weatherApiService;

    @Test
    public void shouldBeReturnThirdPartyApiError() throws Exception {
        when(weatherApiService.getTemperatureByCoordinates(anyDouble(), anyDouble()))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(get("/tips/movies")
                .param("latitude", "-20.63")
                .param("longitude", "-49.65")
                .param("page", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("terceiros")));
    }

    @Test
    public void shouldBeReturnUnknownError() throws Exception {
        when(weatherApiService.getTemperatureByCoordinates(anyDouble(), anyDouble()))
                .thenThrow(new NullPointerException());

        mockMvc.perform(get("/tips/movies")
                .param("latitude", "-20.63")
                .param("longitude", "-49.65")
                .param("page", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("tente novamente mais tarde")));
    }

    @Test
    public void shouldBeReturnThrillerMovies() throws Exception {
        Object response = new ObjectMapper().readValue("{\"main\":{\"temp\":14}}", Object.class);
        when(weatherApiService.getTemperatureByCoordinates(anyDouble(), anyDouble()))
                .thenReturn(response);

        mockMvc.perform(get("/tips/movies")
                .param("latitude", "-20.63")
                .param("longitude", "-49.65")
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].genres[?(@.id==53)].name", containsInAnyOrder("Thriller")));
    }
}
