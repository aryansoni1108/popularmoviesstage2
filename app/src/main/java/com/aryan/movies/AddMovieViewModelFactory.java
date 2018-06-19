package com.aryan.movies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Aryan Soni on 6/16/2018.
 */

public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieDatabase database;
    private final int mMovieId;


    public AddMovieViewModelFactory(MovieDatabase database, int mMovieId) {
        this.database = database;
        this.mMovieId = mMovieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new AddMovieViewModel(database,mMovieId);
    }
}
