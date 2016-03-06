package com.vstechlab.popularmovies.data.db;

import android.provider.BaseColumns;

/**
 * Define table and column name of Movies database.
 */
public class MoviesContract {

    /**
     * Inner class that defines the table content of Favorite movies
     */
    public static final class FavoriteMovies implements BaseColumns {
        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_MOVIE_KEY = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_VOTE_AVG = "vote_avg";
    }
}
