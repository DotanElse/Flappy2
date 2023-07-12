package com.dotan.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dotan.game.states.GameStateManager;
import com.dotan.game.states.MenuState;

public class Flappy2 extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Flappy Bird";
	public static Music bgMusic;

	private GameStateManager gsm;
	private SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		gsm.push(new MenuState(gsm));

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bgMusic.dispose();
	}
}
