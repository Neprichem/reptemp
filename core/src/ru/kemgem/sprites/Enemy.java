package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.kemgem.mainClass;

/**
 * Created by Danil on 21.11.2015.
 */
//класс противка
public class Enemy {
    public static final int ENEMY_WIDTH = 52;

    private Texture enemy;
    private Vector2 posEnemy;
    private Rectangle boundsEnemy;

    public Texture getEnemy() {
        return enemy;
    }

    public Vector2 getPosEnemy() {
        return posEnemy;
    }

    public Enemy(float x, float y){
        enemy = new Texture("enemy.png");

        posEnemy = new Vector2(x, mainClass.HEIGHT/4);

        boundsEnemy = new Rectangle(posEnemy.x, posEnemy.y, enemy.getWidth(), enemy.getHeight());
    }

    public void reposition(float x){
        posEnemy.set(x, mainClass.HEIGHT/4);
        boundsEnemy.setPosition(posEnemy.x, posEnemy.y);
    }


    public boolean collides(Rectangle player){
        return player.overlaps(boundsEnemy);
    }

    public void dispose() { enemy.dispose(); }
}
