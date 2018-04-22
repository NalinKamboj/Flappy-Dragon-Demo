package com.boltinc.flappydragon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Random randomPos;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;

    public Tube(float x){
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");
        randomPos = new Random();
        posTopTube = new Vector2(x, randomPos.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public void reposition(int x) {
        posTopTube.set(x, randomPos.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);      //Repositioning the tube which has gone out of the viewport
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
    }
}
