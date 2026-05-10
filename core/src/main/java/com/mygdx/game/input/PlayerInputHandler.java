package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerInputHandler {

    public float getPlayerX() {

        if (Gdx.input.isTouched()) {
            return Gdx.input.getX() - 32;
        }

        return -1;
    }

    public boolean canShoot() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
}
