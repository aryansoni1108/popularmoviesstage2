package com.aryan.movies;


import java.util.List;

/**
 * Created by Aryan Soni on 6/10/2018.
 */

interface onFetchTaskCompleted {
    void onFetchMoviesTaskCompleted(List<Movies> movies);

}

interface onFetchMovieVideos{
    void onFetchMovieVideosCompleted(List<MovieVideos> moviedetails);
}

interface onFetchMovieReviews{
    void onFetchMovieReviewsCompleted(List<MovieReviews> movieReviews);
}
interface onFetchFavorite{
    void onFetchFavorite(List<Movies> movies);
}
