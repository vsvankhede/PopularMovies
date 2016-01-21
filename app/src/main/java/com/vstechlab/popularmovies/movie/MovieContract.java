package com.vstechlab.popularmovies.movie;

import android.graphics.Bitmap;

import com.vstechlab.popularmovies.LoadDataView;
import com.vstechlab.popularmovies.Presenter;
import com.vstechlab.popularmovies.data.entity.Movie;

public interface MovieContract {

    interface View extends LoadDataView {

        void showTitle(String title);
        void hideTitle();

        void showPoster(String url);
        void hidePoster();

        void showOverview(String overview);
        void hideOverview();

        void showVoteAverage(Double voteAverage);
        void hideVoteAverage();

        void showReleaseDate(String date);
        void hideReleaseDate();
    }

    interface UserActionListener extends Presenter {

        void loadMovieDetails(Movie movie);
    }
}
