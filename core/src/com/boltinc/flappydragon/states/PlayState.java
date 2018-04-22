package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.boltinc.flappydragon.FlappyDemo;
import com.boltinc.flappydragon.sprites.Bird;
import com.boltinc.flappydragon.sprites.Tube;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 3;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird mBird;
    private Texture mPlayBackground;
    private Texture ground;     //TODO add hitbox for ground! Priority: high

    private Array<Tube> mTubes;     //libGDX array. Not standard JAVA array
    private Vector2 groundPos1, groundPos2;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        mBird = new Bird(50, 300);
        mPlayBackground = new Texture("background.png");
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth/2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - (cam.viewportWidth/2)) + ground.getWidth(),GROUND_Y_OFFSET);

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
        updateGround();
        mBird.update(deltaTime);

        //Make camera follow the bird
        cam.position.x = mBird.getPosition().x + 80;        //offset camera a little
        for(int i =0; i<mTubes.size; i++) {         //TODO Use the normal Tube: mTubes for loop. Try getting rid of ITERATOR LIBGDX error which showed up...
            Tube tube = mTubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING)* TUBE_COUNT));
            }
            if(tube.collide(mBird.getBounds())){
                mGameStateManager.setState(new PlayState(mGameStateManager));
            }
        }

        if(mBird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            mGameStateManager.setState(new PlayState(mGameStateManager));
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

        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);           //TODO Ground is currently behind all tubes. Change it to appear on top of all tubes.
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);

        spriteBatch.end();
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth/2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth()*2, 0);
        }
        if (cam.position.x - (cam.viewportWidth/2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth()*2, 0);
        }
    }

    @Override
    public void dispose() {
        mPlayBackground.dispose();
        mBird.dispose();
        for(Tube tube: mTubes)
            tube.dispose();
        System.out.println("Play State Disposed!");
    }
}
