package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.boltinc.flappydragon.FlappyDemo;

import java.util.Calendar;

public class TimerState extends State {
    private final int DEFAULT_COUNT = 3;
    private Calendar rightNow;
    int firstSecond, secondSecond, thirdSecond;
    private int displayNum;

    private Texture mBackground;
    private Texture pixelFontTexture;
    private Sound timerSound;

    BitmapFont pixelFont;

    public TimerState(GameStateManager gameStateManager){
        super(gameStateManager);
        rightNow = Calendar.getInstance();          //Timer implementation is very trivial. TODO: Change timer to be more efficient and sleek.
        firstSecond = rightNow.get(13) + 1;
        secondSecond = firstSecond + 1;
        thirdSecond = secondSecond + 1;
        displayNum = 3;

        System.out.println("SECOND "+firstSecond);
        System.out.println("SECOND "+secondSecond);
        System.out.println("SECOND "+thirdSecond);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

        timerSound = Gdx.audio.newSound(Gdx.files.internal("timer_sfx.ogg"));
        mBackground = new Texture("background.png");
        pixelFontTexture = new Texture(Gdx.files.internal("pixel_font.png"), true);
        pixelFontTexture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        pixelFont = new BitmapFont(Gdx.files.internal("pixel_font.fnt"), new TextureRegion(pixelFontTexture), false);
        pixelFont.setColor(90, 90, 90, 255);
        pixelFont.setUseIntegerPositions(false);
        pixelFont.getData().setScale(0.75f, 0.75f);
//        timerSound.play(1.0f);
    }
    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        if(Calendar.getInstance().get(13) > thirdSecond)
            mGameStateManager.setState(new PlayState(mGameStateManager));   //Fire up the game for real!
        else if(Calendar.getInstance().get(13) > secondSecond) {
            displayNum = 1;
            timerSound.play(1.0f);
        } else if(Calendar.getInstance().get(13) > firstSecond){
            displayNum = 2;
            timerSound.play(1.0f);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(cam.combined);

        spriteBatch.draw(mBackground, 0, 0);
        pixelFont.draw(spriteBatch, "" + displayNum, cam.position.x - (pixelFont.getSpaceWidth()), cam.viewportHeight/2 + cam.viewportWidth/3);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        pixelFont.dispose();
        mBackground.dispose();
    }
}
