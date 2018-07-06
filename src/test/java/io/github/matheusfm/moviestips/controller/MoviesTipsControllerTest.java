package io.github.matheusfm.moviestips.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MoviesTipsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MoviesTipsController controller;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void defaultPage() throws Exception {
        mockMvc.perform(get("/tips/movies")
                .param("latitude", "-20.63")
                .param("longitude", "-49.65"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(1)))
                .andExpect(jsonPath("$.results.[0].genres.[0].name").exists());
    }

    @Test
    public void requiredParameter() throws Exception {
        mockMvc.perform(get("/tips/movies")
                .param("longitude", "-49.65"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("latitude é obrigatório")));
    }

    @Test
    public void parameterType() throws Exception {
        mockMvc.perform(get("/tips/movies")
                .param("latitude", "-20.63")
                .param("longitude", "STRING"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("longitude deve ser do tipo Double")));
    }
}