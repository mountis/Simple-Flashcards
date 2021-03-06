package com.randomappsinc.simpleflashcards.persistence;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.randomappsinc.simpleflashcards.utils.MyApplication;

public class PreferencesManager {

    private static final String FIRST_TIME_USER = "firstTimeUser";
    private static final String NUM_OPENS_KEY = "numOpens";

    private static final int NUM_APP_OPENS_BEFORE_ASKING_FOR_RATING = 5;
    private static final int NUM_APP_OPENS_BEFORE_ASKING_FOR_SHARE = 10;

    private SharedPreferences prefs;
    private static PreferencesManager instance;

    public static PreferencesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized PreferencesManager getSync() {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private PreferencesManager() {
        prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
    }

    public boolean isFirstTimeUser() {
        return prefs.getBoolean(FIRST_TIME_USER, true);
    }

    public void rememberWelcome() {
        prefs.edit().putBoolean(FIRST_TIME_USER, false).apply();
    }

    public void logAppOpen() {
        int currentOpens = prefs.getInt(NUM_OPENS_KEY, 0);
        prefs.edit().putInt(NUM_OPENS_KEY, ++currentOpens).apply();
    }

    public boolean shouldAskForRating() {
        int currentOpens = prefs.getInt(NUM_OPENS_KEY, 0);
        return currentOpens == NUM_APP_OPENS_BEFORE_ASKING_FOR_RATING;
    }

    public boolean shouldAskForShare() {
        int currentOpens = prefs.getInt(NUM_OPENS_KEY, 0);
        return currentOpens == NUM_APP_OPENS_BEFORE_ASKING_FOR_SHARE;
    }
}
