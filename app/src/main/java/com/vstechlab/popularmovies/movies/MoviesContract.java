package com.vstechlab.popularmovies.movies;

import android.content.Context;
import android.net.Uri;
import android.widget.CursorAdapter;

import com.vstechlab.popularmovies.FavoriteMoviesAdapter;
import com.vstechlab.popularmovies.LoadDataView;
import com.vstechlab.popularmovies.Presenter;
import com.vstechlab.popularmovies.data.entity.Movie;

import java.io.IOException;
import java.util.List;

/**
 * Interface representing contract between view and presenter.
 */
public interface MoviesContract {

    interface View extends LoadDataView {

        void showMovies(List<Movie> movies);
        void showMovieDetailUI(Movie movie);
        void clearMovies();
        void updateMenu();
    }

    interface FavoriteMoviesView extends LoadDataView {
        void showMovies(FavoriteMoviesAdapter favoriteMoviesAdapter);
        void updateMenu();
    }

    interface UserActionListener extends Presenter {
        void loadMoviesSortByPopularity();
        void loadMoviesSortByRatting();
        void openMovieDetails();
    }

    interface FavoriteMoviesUserActionListener extends Presenter {
        void loadFavoriteMovies();
        void openMoviesDetails();
    }
}
