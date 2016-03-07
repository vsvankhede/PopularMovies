package com.vstechlab.popularmovies.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.vstechlab.popularmovies.data.db.MoviesContract.FavoriteMovies;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want to start each test with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(FavoriteMovies.TABLE_NAME);

        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MoviesDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created table we want
        Cursor c = db.rawQuery(" SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        assertTrue("Error: Your database is created withour favorite table", tableNameHashSet.isEmpty());

        // now do our table contain the correct column?
        c = db.rawQuery("PRAGMA table_info(" + FavoriteMovies.TABLE_NAME + ")",
                null);
        assertTrue("Error: This means that we were unable to query database for table information",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> favoriteMoviesHashSet = new HashSet<>();
        favoriteMoviesHashSet.add(FavoriteMovies._ID);
        favoriteMoviesHashSet.add(FavoriteMovies.COLUMN_TITLE);
        favoriteMoviesHashSet.add(FavoriteMovies.COLUMN_SUMMARY);
        favoriteMoviesHashSet.add(FavoriteMovies.COLUMN_MOVIE_KEY);
        favoriteMoviesHashSet.add(FavoriteMovies.COLUMN_RELEASE_DATE);
        favoriteMoviesHashSet.add(FavoriteMovies.COLUMN_POSTER);
        favoriteMoviesHashSet.add(FavoriteMovies.COLUMN_VOTE_AVG);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            favoriteMoviesHashSet.remove(columnName);
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required favorite column entry",
                favoriteMoviesHashSet.isEmpty());
        db.close();
    }

    /*
       Here we will write code to test that we can insert and query favorite movie in
       favorite movies table.
     */
    public void testFavoriteMoviesTable() {
        SQLiteDatabase db = new MoviesDbHelper(mContext).getWritableDatabase();

        ContentValues testValues = TestUtilities.createMovieValues(22, mContext);

        long movieRowId;
        movieRowId = db.insert(FavoriteMovies.TABLE_NAME, null, testValues);

        // Verify we got a row back
        assertTrue(movieRowId != -1);

        Cursor c = db.query(
                FavoriteMovies.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Error: no record returns from favorite movies entry", c.moveToFirst());

        TestUtilities.validateCurrentRecord("Error: Favorite movies Query Validation Failed",
                c, testValues);

        assertFalse("Error: More than one record returned from location query",
                c.moveToNext());

        c.close();
        db.close();
    }
}
