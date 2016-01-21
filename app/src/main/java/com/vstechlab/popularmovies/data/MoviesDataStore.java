package com.vstechlab.popularmovies.data;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.vstechlab.popularmovies.data.entity.MovieList;
import com.vstechlab.popularmovies.data.net.MoviesApi;
import com.vstechlab.popularmovies.data.net.SignedInterceptor;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class MoviesDataStore implements MoviesRepository {
    private final String API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXX";
    private final MoviesApi mMoviesApi;

    public MoviesDataStore() {
        OkHttpClient okClient = new OkHttpClient();

        SignedInterceptor signingInterceptor = new SignedInterceptor(API_KEY);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClient.interceptors().add(signingInterceptor);
        okClient.interceptors().add(loggingInterceptor);

        Retrofit movieApiAdapter = new Retrofit.Builder()
                .baseUrl(MoviesApi.BASE_URL)
                .client(okClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMoviesApi = movieApiAdapter.create(MoviesApi.class);
    }

    @Override
    public Call<MovieList> getMoviesSortByPopularity() {
        return mMoviesApi.getMoviesSortByPopularity(MoviesApi.sortByPopularity);
    }

    @Override
    public Call<MovieList> getMoviesSortByRatting() {
        return mMoviesApi.getMoviesSortByRatting(MoviesApi.sortByHighRated);
    }
}
