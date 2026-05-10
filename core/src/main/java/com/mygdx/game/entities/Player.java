package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.input.PlayerInputHandler;

public class Player extends GameObject {

    PlayerInputHandler input;

    float shootCooldown = 0;

    public Player(Texture texture) {

        this.texture = texture;

        x = 400;
        y = 20;

        input = new PlayerInputHandler();
    }

    @Override
    public void update(float delta) {

        float newX = input.getPlayerX();

        if (newX != -1) {
            x = newX;
        }

        shootCooldown -= delta;
    }

    public boolean canShoot() {

        return input.canShoot()
            && shootCooldown <= 0;
    }

    public Bullet shoot(Texture bulletTexture) {

        shootCooldown = 0.3f;

        return new Bullet(
            bulletTexture,
            x + 24,
            y + 60
        );
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, 64, 64);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 64);
    }
}
