package com.vstechlab.popularmovies.data.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MoviesProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDbHelper mOpenHelper;

    static final int FAVORITE = 100;
    static final int FAVORITE_WITH_ID = 200;

    private static final SQLiteQueryBuilder sMoviesQueryBuilder;

    // favorite.movie_id = ?
    private static final String sFavoriteMovieWithId =
            MoviesContract.FavoriteMovies.TABLE_NAME +
                    "." + MoviesContract.FavoriteMovies.COLUMN_MOVIE_KEY + " = ? ";

    static {
        sMoviesQueryBuilder = new SQLiteQueryBuilder();
        sMoviesQueryBuilder.setTables(MoviesContract.FavoriteMovies.TABLE_NAME);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_FAVORITE, FAVORITE);
        matcher.addURI(authority, MoviesContract.PATH_FAVORITE + "/*", FAVORITE_WITH_ID);
        return matcher;
    }

    private Cursor getFavoriteMovies(String[] projection) {

        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                null);
    }

    private Cursor getFavoriteMoviesById(Uri uri, String[] projection) {
        long movieId = MoviesContract.FavoriteMovies.getMovieIdFromUri(uri);

        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sFavoriteMovieWithId,
                new String[]{Long.toString(movieId)},
                null,
                null,
                null);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            //"favorite"
            case FAVORITE:
                retCursor = getFavoriteMovies(projection);
                break;
            case FAVORITE_WITH_ID:
                retCursor = getFavoriteMoviesById(uri, projection);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITE:
                return MoviesContract.FavoriteMovies.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVORITE:
                long _id = db.insert(MoviesContract.FavoriteMovies.TABLE_NAME, null, values);
                if ( _id > 0)
                    returnUri = MoviesContract.FavoriteMovies.buildFavoriteMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if ( null == selection ) selection = "1";
        switch (match) {
            case FAVORITE:
                rowsDeleted = db.delete(MoviesContract.FavoriteMovies.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
