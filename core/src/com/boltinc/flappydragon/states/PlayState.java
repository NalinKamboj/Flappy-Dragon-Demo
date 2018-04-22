package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.boltinc.flappydragon.FlappyDemo;
import com.boltinc.flappydragon.sprite.Bird;
import com.boltinc.flappydragon.sprite.Tube;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 10;

    private Bird mBird;
    private Texture mPlayBackground;
    private Tube mTube;

    private Array<Tube> mTubes;     //libGDX array. Not standard JAVA array

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        mBird = new Bird(50, 300);
        mPlayBackground = new Texture("background.png");
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        mTube = new Tube(100);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            mBird.jump();
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        mBird.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);      //Updating our spritebatch to match the new orthographic camera
        spriteBatch.begin();
        spriteBatch.draw(mPlayBackground, cam.position.x-(cam.viewportWidth/2), 0);
        spriteBatch.draw(mBird.getBirdTexture(), mBird.getPosition().x, mBird.getPosition().y);
        spriteBatch.draw(mTube.getTopTube(), mTube.getPosTopTube().x, mTube.getPosTopTube().y);
        spriteBatch.draw(mTube.getBottomTube(), mTube.getPosBotTube().x, mTube.getPosBotTube().y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
