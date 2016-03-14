package com.vstechlab.popularmovies.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;

/**
 * Manages local database for movies data.
 */
public class MoviesDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE " + FavoriteMovies.TABLE_NAME + " (" +
                FavoriteMovies._ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMovies.COLUMN_MOVIE_KEY + " INTEGER NOT NULL," +
                FavoriteMovies.COLUMN_TITLE + " TEXT NOT NULL," +
                FavoriteMovies.COLUMN_RELEASE_DATE + " INTEGER NOT NULL," +
                FavoriteMovies.COLUMN_POSTER + " TEXT NOT NULL," +
                FavoriteMovies.COLUMN_SUMMARY + " TEXT NOT NULL," +
                FavoriteMovies.COLUMN_VOTE_AVG + " NUMERIC NOT NULL," +
                " UNIQUE (" + FavoriteMovies.COLUMN_MOVIE_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovies.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
