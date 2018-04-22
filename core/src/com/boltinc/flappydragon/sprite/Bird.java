package com.boltinc.flappydragon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private Vector3 mPosition;
    private Vector3 mVelocity;

    private Rectangle bounds;       //Bird hit-box

    private Texture birdTexture;

    public Bird(int x, int y) {
        mPosition = new Vector3(x, y, 0);
        mVelocity = new Vector3(0,0,0);
        birdTexture = new Texture("bird.png");
        bounds = new Rectangle(x, y, birdTexture.getWidth(), birdTexture.getHeight());
    }

    public void update(float dt) {
        if(mPosition.y > 0)
            mVelocity.add(0, GRAVITY, 0);
        mVelocity.scl(dt);
        mPosition.add(MOVEMENT*dt, mVelocity.y, 0);
        if(mPosition.y<0)
            mPosition.y = 0;
        mVelocity.scl(1/dt);
        bounds.setPosition(mPosition.x, mPosition.y);
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public Texture getBirdTexture() {
        return birdTexture;
    }

    public void jump() {
        mVelocity.y = 250;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
