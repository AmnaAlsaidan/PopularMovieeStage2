package com.example.administrator.popularmovies.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoriteMovies.class}, version = 3, exportSchema = false)
public abstract class FavoriteMoviesDB extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "FavMoviesDB";
    private static FavoriteMoviesDB mInstance;

    public static FavoriteMoviesDB getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                Log.i("", "" + FavoriteMoviesDB.DATABASE_NAME);
                mInstance=  Room.databaseBuilder(context.getApplicationContext(),FavoriteMoviesDB.class,"FavMoviesDB").fallbackToDestructiveMigration().build();
            }
        }
        return mInstance;
    }

    public abstract FavoriteMoviesDAO favoriteMoviesDAO();

}
