package com.aryan.movies;

import java.util.List;

/**
 * Created by Aryan Soni on 6/18/2018.
 */

public class savedmovies {

    private List<Movies> movies;
    public savedmovies(){

    }

    public savedmovies(List<Movies> movies) {
        this.movies = movies;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }
}
