package com.boltinc.flappydragon.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State{
    private Texture mBird;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        mBird = new Texture("bird.png");

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(mBird, 50, 50);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
