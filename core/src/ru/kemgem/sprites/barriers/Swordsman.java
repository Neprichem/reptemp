package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.MainClass;

/**
 * Created by Danil on 24.11.2015.
 */
public class Swordsman extends Enemy {
    private static final int MOVEMENT = 100;//скорость

    public Swordsman(float x, float y) {
        super(x, y);

        enemy = new Texture("enemy.png");
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(position.x,position.y, enemy.getWidth(), enemy.getHeight());
    }

    public Vector3 getPosition() { return position; }

    public Texture getTexture() {
        return enemy;
    }

    public void reposition(float x){
        position.set(x, MainClass.HEIGHT/4, 0);
        bounds.setPosition(position.x, position.y);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public void dispose()
    {
        enemy.dispose();
    }
}
