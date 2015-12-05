package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.MainClass;

/**
 * Created by Danil on 24.11.2015.
 */
public class Swordsman extends Enemy {
    private static final int MOVEMENT = -50;//скорость
    private static final int GRAVITY = -15;//гравитация


    public Swordsman(float x, float y) {
        super(x, y);

        enemy = new Texture("enemy.png");
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(position.x,position.y, enemy.getWidth(), enemy.getHeight());
    }
    public Swordsman(Vector3 pos) {
        super(pos.x, pos.y);

        enemy = new Texture("enemy.png");
        position = new Vector3(pos);
        bounds = new Rectangle(position.x,position.y, enemy.getWidth(), enemy.getHeight());
    }

    public void update(float dt){
        position.add(MOVEMENT * dt, 0, 0);//изменение координат движения
        if (position.y < MainClass.HEIGHT/4)//граница пола, чтобы не проваливался
            position.y = MainClass.HEIGHT/4;
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() { return position; }
    public void setPositionY(float y) { position.y = y;}
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
