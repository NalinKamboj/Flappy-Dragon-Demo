package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boltinc.flappydragon.FlappyDemo;

public class GameOverState extends State {
    private Texture background;
    private Texture gameOverTexture;
    private Sound gameOverSound;
    private int SCORE;

    public GameOverState(GameStateManager gameStateManager, int score) {
        super(gameStateManager);
        SCORE = score;
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("metal_hit.ogg"));
        background = new Texture("background.png");
        gameOverTexture = new Texture("game_over.png");
        gameOverSound.play(1.0f);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()){
            mGameStateManager.setState(new HighScoreState(mGameStateManager, SCORE));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.draw(background, 0, 0);
        spriteBatch.draw(gameOverTexture, cam.position.x - gameOverTexture.getWidth()/2, cam.position.y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameOverTexture.dispose();
        gameOverSound.dispose();
    }
}
