package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {

    float shootCooldown = 0;
    float speed = 400f;
    float width = 64;
    float height = 64;

    public Player(Texture texture) {
        this.texture = texture;
        this.x = 400 - width / 2;
        this.y = 20;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;
        }

        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;

        shootCooldown -= delta;
    }

    public boolean canShoot() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootCooldown <= 0;
    }

    public Bullet shoot(Texture bulletTexture) {
        shootCooldown = 0.3f;
        return new Bullet(bulletTexture, x + width / 2 - 8, y + height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
