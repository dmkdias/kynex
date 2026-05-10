package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.managers.Assets;

public class MainGame extends Game {

    public void create() {

        Assets.load();

        setScreen(new GameScreen());
    }
}
