package com.dotan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dotan.game.Flappy2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dotan.game.PreferencesManager;

public class MenuState extends State{
    private Texture background;
    private Texture playButton;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private int highScore;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        PreferencesManager preferencesManager = new PreferencesManager();
        highScore = preferencesManager.getHighScore();
        cam.setToOrtho(false, Flappy2.WIDTH/2, Flappy2.HEIGHT/2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(); // default
        font.getData().setScale(0.5f);
        font.setColor(Color.WHITE);
        System.out.println("0");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
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

        // Render high score
        font.draw(sb, "High Score: " + highScore, 0, 400 - 10);
        sb.draw(playButton, cam.position.x - playButton.getWidth() / 2, cam.position.y );
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
