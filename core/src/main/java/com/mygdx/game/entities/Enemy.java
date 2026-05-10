package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    private Texture texture;
    private Rectangle bounds;
    public boolean alive = true;
    private float speed = 200f;

    public Enemy(Texture texture, float x, float y) {
        this.texture = texture;
        this.bounds = new Rectangle(x, y, 64, 64);
    }

    public void update(float delta) {
        bounds.y -= speed * delta;

        if (bounds.y + bounds.height < 0) {
            alive = false;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
