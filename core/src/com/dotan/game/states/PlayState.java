package com.dotan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dotan.game.PreferencesManager;
import com.dotan.game.sprites.Bird;
import com.dotan.game.sprites.Tube;
import com.dotan.game.Flappy2;

public class PlayState extends State {
    private static final int TUBE_SPACING = 120;
    private static final int TUBE_COUNT = 4;
    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private int currScore;
    private int highScore;
    private BitmapFont font;
    private Array<Tube> tubes;
    private PreferencesManager preferencesManager;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 200);
        currScore = 0;
        cam.setToOrtho(false, Flappy2.WIDTH/2, Flappy2.HEIGHT/2);
        guiCam.setToOrtho(false, Flappy2.WIDTH/2, Flappy2.HEIGHT/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, -70);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2)+ground.getWidth(), -70);
        preferencesManager = new PreferencesManager();
        highScore = preferencesManager.getHighScore();
        font = new BitmapFont(); // default
        font.getData().setScale(0.5f);
        font.setColor(Color.WHITE);

        tubes = new Array<Tube>();

        for(int i=1; i<=TUBE_COUNT; i++){
            tubes.add(new Tube(i* (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);

        cam.position.x = bird.getPosition().x + 80;

        for(int i=0; i<tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                currScore+=1;
            }

            if(tube.collides(bird.getBounds())){
                endGame();
            }
        }

        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);

        if(bird.getPosition().y < ground.getHeight() + (-70))
            endGame();
        cam.update();
    }

    private void endGame(){
        if (currScore > highScore) {
            preferencesManager.setHighScore(currScore);
        }

        gsm.set(new MenuState(gsm));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        // Render curr score
        // Render high score
        //font.draw(sb, "High Score: " + highScore, bird.getPosition().x-40, 400 - 10); // Adjust y position as needed

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBotTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();

        sb.setProjectionMatrix(guiCam.combined);
        sb.begin();
        font.draw(sb, "Current Score: " + currScore, 0, 400);
        font.draw(sb, "High Score: " + highScore, 0, 400 - 10);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        font.dispose();
        for(Tube tube : tubes)
            tube.dispose();
        System.out.println("Play state disposed, score: " + currScore);
    }
}
