package com.aryan.movies;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Aryan Soni on 6/13/2018.
 */

@Dao
public interface  MovieDao {
    @Query("SELECT * FROM MovieDetails ORDER BY id")
    LiveData<List<Movies>> loadAllMovies();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movies moviePojo);
    @Delete
    void deleteTask(Movies moviePojo);
    @Query("SELECT * FROM MovieDetails WHERE id = :id")
    LiveData<Movies> loadTaskById(int id);



}