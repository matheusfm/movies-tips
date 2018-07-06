package io.github.matheusfm.moviestips.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GenreByTemperatureTest {
    /**
     * Acima de 41
     */
    @Test
    public void action() {
        assertNotEquals(28, GenreByTemperature.of(40).longValue());
        assertEquals(28, GenreByTemperature.of(41).longValue());
    }

    /**
     * Entre 36 e 40
     */
    @Test
    public void comedy() {
        assertNotEquals(35, GenreByTemperature.of(35).longValue());
        assertEquals(35, GenreByTemperature.of(40).longValue());
    }

    /**
     * Entre 21 e 35
     */
    @Test
    public void animation() {
        assertNotEquals(16, GenreByTemperature.of(20).longValue());
        assertEquals(16, GenreByTemperature.of(35).longValue());
    }

    /**
     * Entre 1 e 20
     */
    @Test
    public void thriller() {
        assertNotEquals(53, GenreByTemperature.of(0).longValue());
        assertEquals(53, GenreByTemperature.of(20).longValue());
    }

    /**
     * Menor ou igual a 0
     */
    @Test
    public void documentary() {
        assertEquals(99, GenreByTemperature.of(-1).longValue());
        assertEquals(99, GenreByTemperature.of(0).longValue());
        assertNotEquals(99, GenreByTemperature.of(1).longValue());
    }
}