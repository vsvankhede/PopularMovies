package com.vstechlab.popularmovies.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.utils.Utils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies.COLUMN_MOVIE_KEY;
import static com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies.COLUMN_POSTER;
import static com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies.COLUMN_RELEASE_DATE;
import static com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies.COLUMN_SUMMARY;
import static com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies.COLUMN_TITLE;
import static com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies.COLUMN_VOTE_AVG;

public class TestUtilities extends AndroidTestCase{
    private static final String DUMMY_SUMMARY = "dummy summery of movie";
    private static final String DUMMY_TITLE = "Avenger";
    private static final double DUMMY_RATING_AVG = 7.5;

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            if (columnName == MoviesContract.FavoriteMovies.COLUMN_POSTER) {
                // Todo test blob
                //assertEquals("Value '" + valueCursor.getBlob(idx).toString() + "' did not match the expected value '"
                  //      + expectedValue +"'", expectedValue, valueCursor.getBlob(idx).toString());
            } else {
                assertEquals("Value '" + valueCursor.getString(idx) + "' did not match the expected value '"
                        + expectedValue +"'", expectedValue, valueCursor.getString(idx));
            }
        }
    }

    static ContentValues createMovieValues(int movieRowId, Context context) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(COLUMN_MOVIE_KEY, movieRowId);
        movieValues.put(COLUMN_POSTER, Utils.bitmapToByteArray(getBitmap(context)));
        movieValues.put(COLUMN_RELEASE_DATE, new Date().getTime());
        movieValues.put(COLUMN_SUMMARY, DUMMY_SUMMARY);
        movieValues.put(COLUMN_TITLE, DUMMY_TITLE);
        movieValues.put(COLUMN_VOTE_AVG, DUMMY_RATING_AVG);

        return movieValues;
    }

    static Bitmap getBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(),
                R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
    }
}
