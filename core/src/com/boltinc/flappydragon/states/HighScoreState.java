package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.boltinc.flappydragon.FlappyDemo;

public class HighScoreState extends State {
    private Texture background;
    private Texture pixelFontTexture;
    private Texture mainFontTexture;
    private int SCORE;
    private int previousHighScore;

    private BitmapFont pixelFont;
    private BitmapFont mainFont;
    private Preferences highScorePreferences;

    protected HighScoreState(GameStateManager gameStateManager, int score) {
        super(gameStateManager);
        SCORE = score;

        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture("background.png");

        pixelFontTexture = new Texture(Gdx.files.internal("pixel_font.png"), true);
        pixelFontTexture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        pixelFont = new BitmapFont(Gdx.files.internal("pixel_font.fnt"), new TextureRegion(pixelFontTexture), false);
        pixelFont.setColor(255, 255, 0, 255);
        pixelFont.setUseIntegerPositions(false);
        pixelFont.getData().setScale(0.45f, 0.45f);

        mainFontTexture =  new Texture(Gdx.files.internal("main_font.png"), true);       //mip-mapping enabled for downscaling the font
        mainFontTexture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        mainFont = new BitmapFont(Gdx.files.internal("main_font.fnt"), new TextureRegion(mainFontTexture), false);
        mainFont.setColor(0, 0, 0, 255);
        mainFont.setUseIntegerPositions(false);
        mainFont.getData().setScale(0.4f, 0.4f);

//        testScore = highScorePreferences.getInteger("Sample Name");

        highScorePreferences = Gdx.app.getPreferences("HIGH_SCORE");
        previousHighScore = highScorePreferences.getInteger("HIGH_SCORE", 0);
        System.out.println("HIGH SCORE BEFORE THE GAME: " + previousHighScore);
        if(SCORE > previousHighScore){
            highScorePreferences.putInteger("HIGH_SCORE", SCORE);
            highScorePreferences.flush();
        }
        System.out.println("CURRENT SCORE: " + SCORE);
        System.out.println("HIGH SCORE STATE AFTER: " + highScorePreferences.getInteger("HIGH_SCORE"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()){
            mGameStateManager.setState(new MenuState(mGameStateManager));
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
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.draw(background, 0, 0);
        pixelFont.draw(spriteBatch, "Your Score: " + SCORE, cam.position.x - (cam.viewportWidth/2) + 15, 2 * (cam.viewportHeight/3));
        mainFont.draw(spriteBatch, "Previous High Score: " + previousHighScore, cam.position.x - (cam.viewportWidth/4) - 5, cam.viewportHeight/4);   //TODO UPDATE UI TO SHOW LATEST HIGHSCORES
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        mainFont.dispose();
        pixelFont.dispose();
        background.dispose();
        pixelFontTexture.dispose();
        mainFontTexture.dispose();
    }
}
