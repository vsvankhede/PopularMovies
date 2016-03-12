package com.vstechlab.popularmovies.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.vstechlab.popularmovies.R;
import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;
import com.vstechlab.popularmovies.utils.PollingCheck;
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
    private static final int DUMMY_MOVIE_ROW_ID = 201;
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
            if (columnName == FavoriteMovies.COLUMN_POSTER) {
                // Todo test blob
                //assertEquals("Value '" + valueCursor.getBlob(idx).toString() + "' did not match the expected value '"
                  //      + expectedValue +"'", expectedValue, valueCursor.getBlob(idx).toString());
            } else {
                assertEquals(columnName + ": Value '" + valueCursor.getString(idx) + "' did not match the expected value '"
                        + expectedValue +"'", expectedValue, valueCursor.getString(idx));
            }
        }
    }

    public static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Emptry cursor returned." + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
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

    public static ContentValues createFavoriteMovieValues(Context context) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(COLUMN_MOVIE_KEY, DUMMY_MOVIE_ROW_ID);
        movieValues.put(COLUMN_POSTER, Utils.bitmapToByteArray(getBitmap(context)));
        movieValues.put(COLUMN_RELEASE_DATE, new Date().getDate());
        movieValues.put(COLUMN_SUMMARY, DUMMY_SUMMARY);
        movieValues.put(COLUMN_TITLE, DUMMY_TITLE);
        movieValues.put(COLUMN_VOTE_AVG, DUMMY_RATING_AVG);

        return movieValues;
    }

    public static long insertFavoriteMovieValues(Context context) {
        MoviesDbHelper dbHelper = new MoviesDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createFavoriteMovieValues(context);


        long favoriteMovieRowId = db.insert(FavoriteMovies.TABLE_NAME, null, testValues);

        assertTrue("Error: Failure to insert Favorite movie values", favoriteMovieRowId != -1);

        return favoriteMovieRowId;
    }

    static Bitmap getBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(),
                R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
    }

    public static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        public static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        public TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            this.mHT = ht;
        }

        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            new PollingCheck(5000){

                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
