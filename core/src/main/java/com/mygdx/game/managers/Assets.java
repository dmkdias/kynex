package com.mygdx.game.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static AssetManager manager;

    public static void load() {
        manager = new AssetManager();

        manager.load("player.png", Texture.class);
        manager.load("enemy.png", Texture.class);
        manager.load("bullet.png", Texture.class);

        manager.finishLoading();
    }

    public static Texture getPlayer() {
        return manager.get("player.png", Texture.class);
    }

    public static Texture getEnemy() {
        return manager.get("enemy.png", Texture.class);
    }

    public static Texture getBullet() {
        return manager.get("bullet.png", Texture.class);
    }

    public static void dispose() {
        if (manager != null) manager.dispose();
    }
}
