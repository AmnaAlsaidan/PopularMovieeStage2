package com.example.administrator.popularmovies.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.administrator.popularmovies.models.Movies;

import java.util.List;

@Dao
public interface FavoriteMoviesDAO {
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavoriteMovies favMovie);
    @Query("SELECT * FROM FavMovies ORDER BY movieId")
    LiveData<List<FavoriteMovies>> loadAllFavoriteMovies();
    @Delete
    void delete(FavoriteMovies favMovie);
    @Query("SELECT * FROM FavMovies WHERE movieId = :id")
    FavoriteMovies loadMovieById(int id);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(FavoriteMovies favMovie);
*/



    @Query("SELECT * FROM FavMovies ORDER BY movieId")
    LiveData<List<FavoriteMovies>> loadAllMovies();

    @Insert
    void insertMovie(FavoriteMovies favMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(FavoriteMovies favMovie);

    @Delete
    void deleteMovie(FavoriteMovies favMovie);

    @Query("SELECT * FROM FavMovies WHERE movieId = :id")
    FavoriteMovies loadMovieById(int id);
}
