package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.interfaces.Renderable;
import com.mygdx.game.interfaces.Updatable;

public abstract class GameObject
    implements Renderable, Updatable {

    protected Texture texture;

    protected float x;
    protected float y;

    public boolean alive = true;

    public abstract Rectangle getBounds();
}
