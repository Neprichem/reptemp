package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
/**
 * Created by Danil on 21.11.2015.
 */
public class Enemy {
    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;

    private Texture enemy;
    private Vector2 posEnemy;
    private Random rand;
    private Rectangle boundsEnemy;

    public Texture getEnemy() {
        return enemy;
    }

    public Vector2 getPosEnemy() {
        return posEnemy;
    }

    public Enemy(float x){
        enemy = new Texture("enemy.jpg");
        rand = new Random();

        posEnemy = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);

        boundsEnemy = new Rectangle(posEnemy.x, posEnemy.y, enemy.getWidth(), enemy.getHeight());

    }

    public void reposition(float x){
        posEnemy.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        boundsEnemy.setPosition(posEnemy.x, posEnemy.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsEnemy);
    }
}
