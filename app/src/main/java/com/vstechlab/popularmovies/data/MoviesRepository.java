package com.vstechlab.popularmovies.data;

import android.content.Context;
import android.database.Cursor;

import retrofit.Call;

public interface MoviesRepository {
    Call getMoviesSortByPopularity();
    Call getMoviesSortByRatting();
    Cursor getFavoriteMovies(Context context);
}
