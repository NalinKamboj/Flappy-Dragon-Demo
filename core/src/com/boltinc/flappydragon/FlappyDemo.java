package com.boltinc.flappydragon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boltinc.flappydragon.states.GameStateManager;
import com.boltinc.flappydragon.states.MenuState;

public class FlappyDemo extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private Music music;

	public static final String TITLE = "Flappy Dragon";
	private GameStateManager mGameStateManager;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		mGameStateManager = new GameStateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		//music.play();			//Uncomment to play looping retro music
        mGameStateManager.push(new MenuState(mGameStateManager));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mGameStateManager.update(Gdx.graphics.getDeltaTime());
		mGameStateManager.render(batch);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
	}
}
