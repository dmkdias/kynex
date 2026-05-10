package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class Bullet extends GameObject {

    public Bullet(Texture texture, float x, float y) {

        this.texture = texture;

        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float delta) {

        y += 400 * delta;

        if (y > 600) {
            alive = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(texture, x, y, 16, 32);
    }

    @Override
    public Rectangle getBounds() {

        return new Rectangle(x, y, 16, 32);
    }
}
