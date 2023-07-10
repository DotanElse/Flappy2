package com.dotan.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {
    private static final String PREFERENCES_NAME = "Flappy2Preferences";
    private static final String HIGH_SCORE_KEY = "HighScore";

    private Preferences preferences;

    public PreferencesManager() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public int getHighScore() {
        return preferences.getInteger(HIGH_SCORE_KEY, 0);
    }

    public void setHighScore(int score) {
        preferences.putInteger(HIGH_SCORE_KEY, score);
        preferences.flush();
    }

}
