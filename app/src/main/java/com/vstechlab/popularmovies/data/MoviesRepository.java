package com.vstechlab.popularmovies.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import retrofit.Call;

public interface MoviesRepository {
    Call getMoviesSortByPopularity();
    Call getMoviesSortByRatting();
    CursorLoader getFavoriteMovies(Context context);
    CursorLoader getFavoriteMovieDetail(Context context, Uri uri);
}
