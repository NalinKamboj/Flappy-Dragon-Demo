package com.boltinc.flappydragon.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> mStates;

    public GameStateManager() {
        mStates = new Stack<State>();
    }

    public void push(State state) {
        mStates.push(state);
    }

    public void pop(){
        mStates.pop();
    }

    public void setState(State state){
        mStates.pop();
        mStates.push(state);
    }

    public void update(float dt){
        mStates.peek().update(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        mStates.peek().render(spriteBatch);
    }
}
