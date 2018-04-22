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
    private static final int TUBE_COUNT = 3;

    private Bird mBird;
    private Texture mPlayBackground;

    private Array<Tube> mTubes;     //libGDX array. Not standard JAVA array

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        mBird = new Bird(50, 300);
        mPlayBackground = new Texture("background.png");
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        mTubes = new Array<Tube>();
        for(int i=0; i < TUBE_COUNT; i++) {
            mTubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
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

        //Make camera follow the bird
        cam.position.x = mBird.getPosition().x + 80;        //offset camera a little
        for(Tube tube: mTubes) {
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING)* TUBE_COUNT));
            }
            if(tube.collide(mBird.getBounds())){
                mGameStateManager.setState(new PlayState(mGameStateManager));
            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);      //Updating our spritebatch to match the new orthographic camera
        spriteBatch.begin();
        spriteBatch.draw(mPlayBackground, cam.position.x-(cam.viewportWidth/2), 0);
        spriteBatch.draw(mBird.getBirdTexture(), mBird.getPosition().x, mBird.getPosition().y);
        for(Tube tube: mTubes) {
            spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
