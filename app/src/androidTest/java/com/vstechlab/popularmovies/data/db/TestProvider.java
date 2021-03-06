package com.vstechlab.popularmovies.data.db;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.test.AndroidTestCase;

import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;
import com.vstechlab.popularmovies.utils.TestUtilities;

import static com.vstechlab.popularmovies.utils.TestUtilities.TestContentObserver.getTestContentObserver;

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void deleteAllRecordFromProvider() {
        mContext.getContentResolver().delete(
                FavoriteMovies.CONTENT_URI,
                null,
                null);

        Cursor cursor = mContext.getContentResolver().query(
                FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from Favorite movies tables during delete", 0, cursor.getCount());
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void testFavoriteMovieWithIdQuery() {
        deleteAllRecordFromProvider();
        ContentValues values = TestUtilities.createFavoriteMovieValues(mContext);
        long favoriteMovieRowId = TestUtilities.insertFavoriteMovieValues(mContext);

        assertTrue("Error: Failed to insert favorite movie", favoriteMovieRowId != -1);

        Uri uri = FavoriteMovies.buildFavoriteMovieUri(TestUtilities.DUMMY_MOVIE_ROW_ID);

        Cursor favoriteMovieCursor = mContext.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null,
                null);
        TestUtilities.validateCursor("testFavoriteMovieWithIdQuery, favorite movie with id query", favoriteMovieCursor, values);
        if ( Build.VERSION.SDK_INT >= 19 ) {
            assertEquals("Error: Location Query did not properly set NotificationUri",
                    favoriteMovieCursor.getNotificationUri(), uri);
        }
    }

    public void testInsertFavoriteMovieProvider() {
        ContentValues testValues = TestUtilities.createFavoriteMovieValues(mContext);

        TestUtilities.TestContentObserver tco = getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(FavoriteMovies.CONTENT_URI, true, tco);
        Uri uri = mContext.getContentResolver().insert(FavoriteMovies.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long favoriteMovieRowId = ContentUris.parseId(uri);

        assertTrue(favoriteMovieRowId != -1);

        Cursor cursor = mContext.getContentResolver().query(
                FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null);

        TestUtilities.validateCursor("testInsertFavoriteMovieProvider. Error validating Favorite movie entery.",
                cursor, testValues);


    }

    public void testDeleteRecord() {
        testInsertFavoriteMovieProvider();

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(FavoriteMovies.CONTENT_URI, true,tco);

        deleteAllRecordFromProvider();

        tco.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(tco);
    }
}
