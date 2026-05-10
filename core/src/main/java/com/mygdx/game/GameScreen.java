package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.Assets;

public class GameScreen implements Screen {

    SpriteBatch batch;

    Texture playerTexture;
    Texture enemyTexture;
    Texture bulletTexture;

    BitmapFont font;

    Player player;

    Array<Bullet> bullets;
    Array<Enemy> enemies;

    float spawnTimer = 0;

    int score = 0;

    boolean gameOver = false;

    @Override
    public void show() {

        batch = new SpriteBatch();

        playerTexture = Assets.getPlayer();
        enemyTexture = Assets.getEnemy();
        bulletTexture = Assets.getBullet();

        font = new BitmapFont();

        player = new Player(playerTexture);

        bullets = new Array<>();
        enemies = new Array<>();
    }

    void update(float delta) {

        player.update(delta);

        if (player.canShoot()) {

            bullets.add(
                player.shoot(bulletTexture)
            );
        }

        for (Bullet b : bullets) {
            b.update(delta);
        }

        for (Enemy e : enemies) {
            e.update(delta);
        }

        spawnTimer += delta;

        if (spawnTimer > 1f) {

            enemies.add(
                new Enemy(enemyTexture)
            );

            spawnTimer = 0;
        }

        for (Bullet b : bullets) {

            for (Enemy e : enemies) {

                if (b.getBounds().overlaps(e.getBounds())) {

                    b.alive = false;
                    e.alive = false;

                    score++;
                }
            }
        }

        for (Enemy e : enemies) {

            if (e.getBounds().overlaps(player.getBounds())) {

                gameOver = true;
            }
        }

        for (int i = bullets.size - 1; i >= 0; i--) {

            if (!bullets.get(i).alive) {

                bullets.removeIndex(i);
            }
        }

        for (int i = enemies.size - 1; i >= 0; i--) {

            if (!enemies.get(i).alive) {

                enemies.removeIndex(i);
            }
        }
    }

    void restart() {

        score = 0;

        gameOver = false;

        bullets.clear();
        enemies.clear();

        player = new Player(playerTexture);
    }

    @Override
    public void render(float delta) {

        if (gameOver &&
            Gdx.input.isKeyJustPressed(Input.Keys.R)) {

            restart();
        }

        if (!gameOver) {
            update(delta);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        player.draw(batch);

        for (Bullet b : bullets) {
            b.draw(batch);
        }

        for (Enemy e : enemies) {
            e.draw(batch);
        }

        font.draw(
            batch,
            "Score: " + score,
            20,
            580
        );

        if (gameOver) {

            font.draw(
                batch,
                "GAME OVER",
                320,
                320
            );

            font.draw(
                batch,
                "Press R to Restart",
                260,
                280
            );
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

        batch.dispose();
        font.dispose();
    }
}
