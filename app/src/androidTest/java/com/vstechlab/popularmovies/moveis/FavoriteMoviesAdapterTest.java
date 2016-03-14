package com.vstechlab.popularmovies.moveis;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.view.View;

import com.vstechlab.popularmovies.data.db.MoviesContract;
import com.vstechlab.popularmovies.data.db.MoviesDbHelper;
import com.vstechlab.popularmovies.data.db.TestUtilities;
import com.vstechlab.popularmovies.movies.FavoriteMoviesAdapter;

public class FavoriteMoviesAdapterTest extends AndroidTestCase {
    private FavoriteMoviesAdapter mMovieAdapter;
    private Cursor mTestCursor;

    private void deleteAllRecordsFromDB() {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(MoviesContract.FavoriteMovies.TABLE_NAME, null, null);
        db.close();
    }

    private void deleteAllRecords() {
        deleteAllRecordsFromDB();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();

        // Create test cursor
        ContentValues testValues = TestUtilities.createFavoriteMovieValues(mContext);

        // TestContentObserver tco = TestContentObserver.getTestContentObserver();
        Uri uri = mContext.getContentResolver().insert(MoviesContract.FavoriteMovies.CONTENT_URI, testValues);

        // tco.waitForNotificationOrFail();
        // mContext.getContentResolver().unregisterContentObserver(tco);

        long favoriteMovieRowId = ContentUris.parseId(uri);

        assertTrue(favoriteMovieRowId != -1);

        mTestCursor = mContext.getContentResolver().query(
                MoviesContract.FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null);

//        TestUtilities.validateCursor("testInsertFavoriteMovieProvider. Error validating Favorite movie entery.",
//                mTestCursor, testValues);

        mMovieAdapter = new FavoriteMoviesAdapter(mContext, mTestCursor, 0);
    }

    public void testGetItem() {
        assertNotNull("Movies poster is not set to imageview", mMovieAdapter.getItem(0));
    }

    public void testBindView() {

        View view = mMovieAdapter.getView(0, null, null);
        // view.getId();
        assertNotNull("Movies Poster is not set",view);
    }

}