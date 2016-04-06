package com.vstechlab.popularmovies.movie;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.data.entity.Movie;

public class MovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_FAVORITE_MOVIE = "extra_favorite_movie";

    public static Intent getStartIntent (Activity activity, Movie movie){
        Intent intent = new Intent(activity, MovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    public static Intent getFavoriteMovieStartIntent(Activity activity, @NonNull Cursor cursor) {
        int movieColumnIdx = cursor.getColumnIndex(MoviesContract.FavoriteMovies.COLUMN_MOVIE_KEY);

        return new Intent(activity, MovieActivity.class)
                .setData(MoviesContract.FavoriteMovies.buildFavoriteMovieUri(cursor.getInt(movieColumnIdx)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            if (getIntent().getParcelableExtra(EXTRA_MOVIE) != null) {
                arguments.putParcelable(MovieFragment.EXTRA_MOVIE, getIntent().getParcelableExtra(EXTRA_MOVIE));
            } else {
                arguments.putParcelable(MovieFragment.DETAIL_URI, getIntent().getData());
            }

            MovieFragment fragment = new MovieFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }


        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
