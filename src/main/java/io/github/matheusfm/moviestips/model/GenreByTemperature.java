package io.github.matheusfm.moviestips.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.IntPredicate;
import java.util.stream.Stream;

/**
 * Enum de gênero com o ID e a regra a partir da temperatura
 */
@AllArgsConstructor
public enum GenreByTemperature {
    ACTION(28, temp -> temp > 40),
    COMEDY(35, temp -> temp > 35 && temp <= 40),
    ANIMATION(16, temp -> temp > 20 && temp <= 35),
    THRILLER(53, temp -> temp > 0 && temp <= 20),
    DOCUMENTARY(99, temp -> temp <= 0);

    @Getter
    private int id;
    private IntPredicate predicate;

    public static Integer of(Integer temperature) {
        return Stream.of(values())
                .filter(r -> r.predicate.test(temperature))
                .findFirst()
                .map(GenreByTemperature::getId)
                .get();
    }
}
