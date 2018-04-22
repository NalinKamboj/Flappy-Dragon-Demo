package com.boltinc.flappydragon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Random randomPos;
    private Rectangle boundsTop, boundsBot;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int TUBE_WIDTH = 52;        //we know our texture is 52px for now...

    public Tube(float x){
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");
        randomPos = new Random();
        posTopTube = new Vector2(x, randomPos.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());     //Collision hit-boxes for both tubes (top and bottom)
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

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

    public void reposition(float x) {
        posTopTube.set(x, randomPos.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);      //Repositioning the tube which has gone out of the viewport
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collide(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }
}
