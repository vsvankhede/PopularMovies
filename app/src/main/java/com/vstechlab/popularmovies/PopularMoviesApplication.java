package com.vstechlab.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by vsvankhede on 3/5/2016.
 */
public class PopularMoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
