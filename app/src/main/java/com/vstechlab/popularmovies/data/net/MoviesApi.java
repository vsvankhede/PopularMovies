package com.vstechlab.popularmovies.data.net;

import com.vstechlab.popularmovies.data.entity.MovieList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MoviesApi {

    String BASE_URL = "http://api.themoviedb.org/3/";

    String PARAM_API_KEY = "api_key";

    String sortByPopularity = "popularity.desc";
    String sortByHighRated = "vote_average.desc";
    String favorite = "favorite";

    @GET("discover/movie")
    Call<MovieList> getMoviesSortByPopularity(@Query("sort_by") String sortByPopularity);

    @GET("discover/movie")
    Call<MovieList> getMoviesSortByRatting(@Query("sort_by") String sortByHighRated);
}
