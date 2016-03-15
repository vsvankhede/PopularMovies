package com.vstechlab.popularmovies.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import retrofit.Call;

public interface MoviesRepository {
    Call getMoviesSortByPopularity();
    Call getMoviesSortByRatting();
    CursorLoader getFavoriteMovies(Context context);
}
