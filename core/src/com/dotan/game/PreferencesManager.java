package com.dotan.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {
    private static final String PREFERENCES_NAME = "Flappy2Preferences";
    private static final String HIGH_SCORE_KEY = "HighScore";
    private static final String SOUND_ON_KEY = "SoundOn";

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

    public boolean getSoundOn() { return preferences.getBoolean(SOUND_ON_KEY, true);}

    public void toggleSound() {
        preferences.putBoolean(SOUND_ON_KEY, !getSoundOn());
        preferences.flush();
    }

}
