package com.aryan.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


/**
 * Created by Aryan Soni on 6/16/2018.
 */

public class AddMovieViewModel extends ViewModel {
    private LiveData<Movies> movie;
    public AddMovieViewModel(MovieDatabase database,int id){
        movie = database.movieDao().loadTaskById(id);
    }
    public LiveData<Movies> getMovie(){
        return movie;
    }
}
