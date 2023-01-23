package com.andreick.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

public class MovieDto {

    private final String title;
    private final String imageUrl;
    private final String rating;

    @JsonCreator
    public MovieDto(@JsonProperty("title") String title,
                    @JsonProperty("image") String imageUrl,
                    @JsonProperty("imDbRating") String rating) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return Objects.equals(title, movieDto.title)
                && Objects.equals(imageUrl, movieDto.imageUrl)
                && Objects.equals(rating, movieDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imageUrl, rating);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MovieDto.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("imageUrl='" + imageUrl + "'")
                .add("rating='" + rating + "'")
                .toString();
    }
}
