package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends GameObject {

    public Enemy(Texture texture) {

        this.texture = texture;

        x = (float)Math.random() * 760;
        y = 600;
    }

    public void update(float delta) {

        y -= 150 * delta;

        if (y < 0) {
            alive = false;
        }
    }

    public void draw(SpriteBatch batch) {

        batch.draw(texture, x, y, 64, 64);
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, 64, 64);
    }
}
