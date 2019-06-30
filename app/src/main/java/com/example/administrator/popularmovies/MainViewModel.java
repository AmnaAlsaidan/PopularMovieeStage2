package com.example.administrator.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.popularmovies.DB.FavoriteMovies;
import com.example.administrator.popularmovies.DB.FavoriteMoviesDB;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoriteMovies>> favoriteMovie;

    public MainViewModel(Application application) {
        super(application);
        FavoriteMoviesDB favoriteMoviesDB = FavoriteMoviesDB.getInstance(this.getApplication());
        favoriteMovie = favoriteMoviesDB.favoriteMoviesDAO().loadAllMovies();


    }
    public LiveData<List<FavoriteMovies>> getFavoriteMovies() {
        return favoriteMovie;
    }


}

