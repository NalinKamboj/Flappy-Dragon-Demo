package com.boltinc.flappydragon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.boltinc.flappydragon.FlappyDemo;

public class MenuState extends State {
    private Texture background;
    private Texture playButton;
    private Sound menuSound;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture("background.png");
        playButton = new Texture("play_button.png");
        menuSound = Gdx.audio.newSound(Gdx.files.internal("menu_confirm.ogg"));
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            Vector3 temp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(temp);        //Translates point given in screen coordinates to world space.
            Rectangle playButtonBounds = new Rectangle(cam.position.x - playButton.getWidth()/2, cam.position.y, playButton.getWidth(), playButton.getHeight());
            if(playButtonBounds.contains(temp.x, temp.y)) {
//                mGameStateManager.setState(new PlayState(mGameStateManager));
                menuSound.play(1.0f);
                mGameStateManager.setState(new TimerState(mGameStateManager));
            }
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
        spriteBatch.draw(playButton, cam.position.x - playButton.getWidth()/2, cam.position.y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        System.out.println("Menu State Disposed");
    }
}
