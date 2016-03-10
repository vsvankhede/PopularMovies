package com.vstechlab.popularmovies.data.db;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.test.AndroidTestCase;

import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;
import com.vstechlab.popularmovies.movie.MovieContract;

import java.security.Provider;

public class TestProvider extends AndroidTestCase {

    private void deleteAllRecordsFromDB() {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(FavoriteMovies.TABLE_NAME, null, null);
        db.close();
    }

    private void deleteAllRecords() {
        deleteAllRecordsFromDB();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MoviesProvider.class.getName());

        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("Error: MoviesProvider registered with authority: " + providerInfo.authority +
                        " instead of authority: " + MoviesContract.CONTENT_AUTHORITY,
                        providerInfo.authority, MoviesContract.CONTENT_AUTHORITY);

        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: MoviesProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    public void testGetType() {
        String type = mContext.getContentResolver().getType(FavoriteMovies.CONTENT_URI);

        assertEquals("Error: the Favorite CONTENT_URI should return " + FavoriteMovies.CONTENT_TYPE,
                FavoriteMovies.CONTENT_TYPE, type);
    }

    public void testBasicFavoriteMoviesQuery() {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = TestUtilities.createFavoriteMovieValues(mContext);
        long favoriteMovieRowId = TestUtilities.insertFavoriteMovieValues(mContext);

        Cursor favoriteMovieCursor = mContext.getContentResolver().query(
                FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null);

        TestUtilities.validateCursor("testBasicFavoriteMovieQuert, favorite movie query", favoriteMovieCursor, values);

        if ( Build.VERSION.SDK_INT >= 19 ) {
            assertEquals("Error: Location Query did not properly set NotificationUri",
                    favoriteMovieCursor.getNotificationUri(), FavoriteMovies.CONTENT_URI);
        }
    }
}
