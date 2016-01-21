package com.vstechlab.popularmovies.movies;

import com.vstechlab.popularmovies.data.MoviesDataStore;
import com.vstechlab.popularmovies.data.MoviesRepository;

/**
 * Enable injection for {@link com.vstechlab.popularmovies.data.MoviesRepository} at runtime.
 */
public class Injection {

    public static MoviesRepository provideMoviesRepository() {
        return new MoviesDataStore();
    }
}
