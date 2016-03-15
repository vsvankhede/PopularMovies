package com.vstechlab.popularmovies.movie;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

    public static Intent getFavoriteMovieStartIntent(Activity activity, @NonNull Cursor cursor,
                                                     @NonNull int COL_MOVIE_ID) {
        return new Intent(activity, MovieActivity.class)
                .setData(MoviesContract.FavoriteMovies.buildFavoriteMovieUri(cursor.getInt(COL_MOVIE_ID)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
