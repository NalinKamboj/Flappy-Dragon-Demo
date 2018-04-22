package com.boltinc.flappydragon.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int GRAVITY = -15;
    private Vector3 mPosition;
    private Vector3 mVelocity;

    private Texture birdTexture;

    public Bird(int x, int y) {
        mPosition = new Vector3(x, y, 0);
        mVelocity = new Vector3(0,0,0);
        birdTexture = new Texture("bird.png");
    }

    public void update(float dt) {
        if(mPosition.y > 0)
            mVelocity.add(0, GRAVITY, 0);
        mVelocity.scl(dt);
        mPosition.add(0, mVelocity.y, 0);
        if(mPosition.y<0)
            mPosition.y = 0;
        mVelocity.scl(1/dt);
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
}
