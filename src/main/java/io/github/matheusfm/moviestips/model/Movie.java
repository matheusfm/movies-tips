package io.github.matheusfm.moviestips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;

@JsonPropertyOrder(alphabetic=true)
@ToString
@EqualsAndHashCode
@ApiModel
public class Movie {
    private String backdropPath;
    private Collection<Genre> genres;
    private Collection<Integer> genreIds;
    private Integer id;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String posterPath;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;
    private String title;
    private Float voteAverage;
    private Integer voteCount;

    @ApiModelProperty(notes = "backdrop path", example = "http://image.tmdb.org/t/p/original/mabuNsGJgRuCTuGqjFkWe1xdu19.jpg")
    @JsonProperty("backdropPath")
    public String getBackdropPath() {
        return backdropPath;
    }

    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @ApiModelProperty(notes = "genres")
    public Collection<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Collection<Genre> genres) {
        this.genres = genres;
    }

    @JsonIgnore
    public Collection<Integer> getGenreIds() {
        return genreIds;
    }

    @JsonProperty("genre_ids")
    public void setGenreIds(Collection<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    @ApiModelProperty(notes = "id", example = "260513")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "original language", example = "en")
    @JsonProperty("originalLanguage")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @ApiModelProperty(notes = "original title", example = "Incredibles 2")
    @JsonProperty("originalTitle")
    public String getOriginalTitle() {
        return originalTitle;
    }

    @JsonProperty("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @ApiModelProperty(notes = "overview", example = "A família de super-heróis favorita de todo mundo está de volta em Os Incríveis 2 — mas dessa vez ...")
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @ApiModelProperty(notes = "poster path", example = "http://image.tmdb.org/t/p/original/y3EEb7o6NxK0pl0WsOswCos663y.jpg")
    @JsonProperty("posterPath")
    public String getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @ApiModelProperty(notes = "release date", example = "2018-06-14")
    @JsonProperty("releaseDate")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("release_date")
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ApiModelProperty(notes = "title", example = "Os Incríveis 2")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ApiModelProperty(notes = "vote average", example = "7.7")
    @JsonProperty("voteAverage")
    public Float getVoteAverage() {
        return voteAverage;
    }

    @JsonProperty("vote_average")
    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @JsonProperty("voteCount")
    public Integer getVoteCount() {
        return voteCount;
    }

    @ApiModelProperty(notes = "vote count", example = "676")
    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
