package com.dotan.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SoundButton {
    private Texture texture;
    private TextureRegion soundOnRegion;
    private TextureRegion soundOffRegion;
    private TextureRegion currentRegion;
    public boolean soundOn;

    public SoundButton(boolean isSoundOn) {
        texture = new Texture("soundIcon.png");
        soundOnRegion = new TextureRegion(texture, 0, 0, texture.getWidth() / 2, texture.getHeight());
        soundOffRegion = new TextureRegion(texture, texture.getWidth() / 2, 0, texture.getWidth() / 2, texture.getHeight());
        soundOn = isSoundOn; // Get the sound pref from MenuState
        currentRegion = soundOnRegion; // Set the initial region to sound on
        if(!soundOn)
            currentRegion = soundOffRegion;
    }

    public void toggleSound() {
        soundOn = !soundOn; // Toggle the sound state

        if (soundOn) {
            currentRegion = soundOnRegion;
        } else {
            currentRegion = soundOffRegion;
        }
    }

    public void render(SpriteBatch sb, float x, float y) {
        sb.draw(currentRegion, x, y, 30, 30);
    }

    public void dispose() {
        texture.dispose();
    }
}
