package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boltinc.flappydragon.FlappyDemo;

public class MenuState extends State {
    private Texture background;
    private Texture playButton;


    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        background = new Texture("background.png");
        playButton = new Texture("play_button.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            mGameStateManager.setState(new PlayState(mGameStateManager));
            dispose();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        spriteBatch.draw(playButton, (FlappyDemo.WIDTH/2) - (playButton.getWidth()/2), (FlappyDemo.HEIGHT/2));
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
