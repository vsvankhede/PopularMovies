package com.vstechlab.popularmovies.data.db;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.vstechlab.popularmovies.utils.TestUtilities;

public class TestUriMatcher extends AndroidTestCase {

    // content://com.vstechlab.popularmovies.app/favorite
    private static final Uri TEST_FAVORITE_URI = MoviesContract.FavoriteMovies.CONTENT_URI;
    private static final Uri TEST_FAVORITE_WITH_ID_URI = MoviesContract.FavoriteMovies
            .buildFavoriteMovieUri(TestUtilities.DUMMY_MOVIE_ROW_ID);

    public void testUriMatcher() {
        UriMatcher testMatcher = MoviesProvider.buildUriMatcher();

        assertEquals("Error: The FAVORITE URI was matched incorrectly.",
                testMatcher.match(TEST_FAVORITE_URI), MoviesProvider.FAVORITE);

        assertEquals("Error: The FAVORITE_WITH_ID wa matched incorrectly.",
                testMatcher.match(TEST_FAVORITE_WITH_ID_URI), MoviesProvider.FAVORITE_WITH_ID);

    }

}
