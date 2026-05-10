package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.Assets;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture playerTexture, enemyTexture, bulletTexture, backgroundTexture;
    private BitmapFont font;
    private Player player;
    private Array<Bullet> bullets;
    private Array<Enemy> enemies;

    private Viewport viewport;
    private final float WORLD_WIDTH = 800;
    private final float WORLD_HEIGHT = 600;

    private float spawnTimer = 0;
    private int score = 0;
    private final int WIN_SCORE = 50;
    private boolean gameOver = false;
    private boolean gameWon = false;

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);

        playerTexture = Assets.getPlayer();
        enemyTexture = Assets.getEnemy();
        bulletTexture = Assets.getBullet();
        backgroundTexture = new Texture("background.png");

        font = new BitmapFont();
        player = new Player(playerTexture);
        bullets = new Array<>();
        enemies = new Array<>();
    }

    private void update(float delta) {
        player.update(delta);

        if (player.getBounds().x < 0) player.getBounds().x = 0;
        if (player.getBounds().x > WORLD_WIDTH - player.getBounds().width)
            player.getBounds().x = WORLD_WIDTH - player.getBounds().width;

        if (player.canShoot()) {
            bullets.add(player.shoot(bulletTexture));
        }

        for (Bullet b : bullets) b.update(delta);
        for (Enemy e : enemies) e.update(delta);

        spawnTimer += delta;
        if (spawnTimer > 1f) {
            float randomX = MathUtils.random(0, WORLD_WIDTH - 64);
            enemies.add(new Enemy(enemyTexture, randomX, WORLD_HEIGHT));
            spawnTimer = 0;
        }

        handleCollisions();
        if (score >= WIN_SCORE) gameWon = true;
        cleanupObjects();
    }

    private void handleCollisions() {
        for (Bullet b : bullets) {
            for (Enemy e : enemies) {
                if (b.getBounds().overlaps(e.getBounds())) {
                    b.alive = false; e.alive = false;
                    score++;
                }
            }
        }
        for (Enemy e : enemies) {
            if (e.getBounds().overlaps(player.getBounds())) gameOver = true;
        }
    }

    private void cleanupObjects() {
        for (int i = bullets.size - 1; i >= 0; i--) if (!bullets.get(i).alive) bullets.removeIndex(i);
        for (int i = enemies.size - 1; i >= 0; i--) if (!enemies.get(i).alive) enemies.removeIndex(i);
    }

    @Override
    public void render(float delta) {
        if ((gameOver || gameWon) && Gdx.input.isKeyJustPressed(Input.Keys.R)) restart();
        if (!gameOver && !gameWon) update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        player.draw(batch);
        for (Bullet b : bullets) b.draw(batch);
        for (Enemy e : enemies) e.draw(batch);

        font.draw(batch, "Score: " + score + " / " + WIN_SCORE, 20, WORLD_HEIGHT - 20);

        if (gameOver) drawCentered("GAME OVER - Press R to Restart");
        else if (gameWon) drawCentered("YOU WIN! Mission Accomplished");

        batch.end();
    }

    private void drawCentered(String text) {
        font.draw(batch, text, WORLD_WIDTH / 2f - 100, WORLD_HEIGHT / 2f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void restart() {
        score = 0; gameOver = false; gameWon = false;
        bullets.clear(); enemies.clear();
        player = new Player(playerTexture);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}
