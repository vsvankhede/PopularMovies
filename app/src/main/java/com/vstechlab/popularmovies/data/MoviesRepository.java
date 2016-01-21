package com.vstechlab.popularmovies.data;

import retrofit.Call;

public interface MoviesRepository {
    Call getMoviesSortByPopularity();
    Call getMoviesSortByRatting();
}
