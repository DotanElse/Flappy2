package com.dotan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.dotan.game.Flappy2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dotan.game.PreferencesManager;
import com.dotan.game.sprites.SoundButton;

public class MenuState extends State{
    private Texture background;
    private Texture playButton;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private int highScore;
    private float buttonX;
    private float buttonY;
    private SoundButton soundIcon;
    private PreferencesManager preferencesManager;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        preferencesManager = new PreferencesManager();
        if (preferencesManager.getSoundOn())
            Flappy2.bgMusic.setVolume(0.05f);
        else
            Flappy2.bgMusic.setVolume(0f);
        highScore = preferencesManager.getHighScore();
        cam.setToOrtho(false, Flappy2.WIDTH/2, Flappy2.HEIGHT/2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
        soundIcon = new SoundButton(preferencesManager.getSoundOn());
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(); // default
        font.getData().setScale(0.5f);
        font.setColor(Color.WHITE);
        System.out.println("0");
        buttonX = (cam.viewportWidth - playButton.getWidth())/2;
        buttonY = (cam.viewportHeight - playButton.getHeight())/2;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            // Unproject touch coordinates based on the camera's projection matrix
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            float touchX = touchPos.x;
            float touchY = touchPos.y;

            if (touchX >= buttonX && touchX <= buttonX + playButton.getWidth() &&
                    touchY >= buttonY && touchY <= buttonY + playButton.getHeight()) {
                gsm.set(new PlayState(gsm));
            }

            if (touchX >= 210 && touchX <= 240 &&
                    touchY >= 370 && touchY <= 400) {
                preferencesManager.toggleSound();
                soundIcon.toggleSound();
                if (preferencesManager.getSoundOn())
                    Flappy2.bgMusic.setVolume(0.05f);
                else
                    Flappy2.bgMusic.setVolume(0f);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        soundIcon.render(sb, 210, 370);

        // Render high score
        font.draw(sb, "High Score: " + highScore, 0, 400 - 10);
        sb.draw(playButton, buttonX, buttonY);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        soundIcon.dispose();
    }
}
