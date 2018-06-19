package com.aryan.movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import java.util.List;

/**
 * Created by Aryan Soni on 6/16/2018.
 */

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<Movies>> movie_saved;
    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        Log.d(TAG,"Actively retrieving the tasks from the Database");
        movie_saved = movieDatabase.movieDao().loadAllMovies();

    }
    public LiveData<List<Movies>> getMovie_saved(){
        return movie_saved;
    }
}
