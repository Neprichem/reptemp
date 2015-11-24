package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danil on 21.11.2015.
 */
//класс противка
public abstract class Enemy {
    public static final int ENEMY_WIDTH = 100;

    protected Texture enemy;
    protected Vector3 position;
    protected Rectangle bounds;

    public Enemy(float x, float y){   }

    public abstract void reposition(float x);
    public abstract boolean collides(Rectangle player);
    public abstract void dispose();
}
