package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Texture ground;
    private Texture mainFontTexture;
    private Sound scoreSound;
    private Sound gameOverSound;          //This sound doesn't play in this class for some reason....weird.

    private Array<Tube> mTubes;     //libGDX array. Not standard JAVA array
    private Vector2 groundPos1, groundPos2;

    private int score;

    BitmapFont mainFont;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);

        score = 0;

        mBird = new Bird(50, 300);
        mPlayBackground = new Texture("background.png");
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth/2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - (cam.viewportWidth/2)) + ground.getWidth(),GROUND_Y_OFFSET);

        //Main standard font
        mainFontTexture =  new Texture(Gdx.files.internal("main_font.png"), true);       //mip-mapping enabled for downscaling the font
        mainFontTexture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        mainFont = new BitmapFont(Gdx.files.internal("main_font.fnt"), new TextureRegion(mainFontTexture), false);
        mainFont.setColor(0, 0, 0, 255);
        mainFont.setUseIntegerPositions(false);
        mainFont.getData().setScale(0.75f, 0.75f);
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("sfx_score.ogg"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("metal_hit.ogg"));

        mTubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++) {
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

        for(int i =0; i<mTubes.size; i++) {         //TODO Use the normal Tube: mTubes for loop. Try getting rid of ITERATOR LIBGDX error which showed up... Priority: Very low
            Tube tube = mTubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING)* TUBE_COUNT));
                score+=1;
                mBird.addSpeed();
                scoreSound.play(0.5f);
            }
            if(tube.collide(mBird.getBounds())){
                gameOverSound.play(1.0f);
                mGameStateManager.setState(new GameOverState(mGameStateManager));
            }
        }

        if(mBird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            gameOverSound.play(1.0f);
            mGameStateManager.setState(new GameOverState(mGameStateManager));
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);      //Updating our spritebatch to match the new orthographic camera
        spriteBatch.begin();
        spriteBatch.draw(mPlayBackground, cam.position.x-(cam.viewportWidth/2), 0);

//        pixelFont.draw(spriteBatch, "3", cam.position.x - (pixelFont.getSpaceWidth()/2), cam.viewportHeight/2 + cam.viewportWidth/3);
//        wait(5);
//        Timer timer = new Timer();
//        timer.delay(20000);

        spriteBatch.draw(mBird.getBirdTexture(), mBird.getPosition().x, mBird.getPosition().y);

        for(Tube tube: mTubes) {
            spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        mainFont.draw(spriteBatch, "Score: " + score, cam.position.x - (cam.viewportWidth/2) + 5, 25);       //+5 for little padding

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
        mainFont.dispose();
        mainFontTexture.dispose();
        scoreSound.dispose();
        for(Tube tube: mTubes)
            tube.dispose();
//        System.out.println("Play State Disposed!");
//        gameOverSound.dispose();
    }
}
