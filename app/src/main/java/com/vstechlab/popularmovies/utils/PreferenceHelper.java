package com.vstechlab.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vstechlab.popularmovies.data.net.MoviesApi;

public class PreferenceHelper  {
    public static final String PREFERENCE = "com.vstechlab.popularmovies.preference";
    public static final String PREFERENCE_SORT = PREFERENCE + ".sort";

    public static String getSortPreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREFERENCE_SORT, MoviesApi.sortByPopularity);
    }

    public static void setSortPreference(Context context, String sortPreference) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCE_SORT, sortPreference);
        editor.apply();
    }

}
