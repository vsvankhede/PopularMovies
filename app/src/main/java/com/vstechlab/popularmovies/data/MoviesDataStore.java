package com.vstechlab.popularmovies.data;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.facebook.stetho.okhttp.StethoInterceptor;
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
    private final String API_KEY = "42940bbff0d5d9073a2800563a42925d";
    private final MoviesApi mMoviesApi;

    public MoviesDataStore() {
        OkHttpClient okClient = new OkHttpClient();

        SignedInterceptor signingInterceptor = new SignedInterceptor(API_KEY);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClient.interceptors().add(signingInterceptor);
        okClient.interceptors().add(loggingInterceptor);
        okClient.networkInterceptors().add(new StethoInterceptor());
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

    @Override
    public CursorLoader getFavoriteMovies(Context context) {
        Uri favoriteMoviesUri = com.vstechlab.popularmovies.data.db.MoviesContract
                .FavoriteMovies.CONTENT_URI;
        return new CursorLoader(context,
                favoriteMoviesUri,
                null,
                null,
                null,
                null);
    }

    @Override
    public CursorLoader getFavoriteMovieDetail(Context context, Uri uri) {

        return new CursorLoader(context,
                uri,
                null,
                null,
                null,
                null);
    }
}
