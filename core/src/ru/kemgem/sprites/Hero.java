package ru.kemgem.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.mainClass;

/**
 * Created by Vitaly on 06.11.2015.
 */
/**
 * redacted by Danil on 21.11.2015.
 */
public class Hero {
    private static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;

    private Texture hero;

    public Hero(int x, int y){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        hero = new Texture("bird.png");
        bounds = new Rectangle(x, y, hero.getWidth(), hero.getHeight());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getHero() {
        return hero;
    }

    public void update(float dt){
        if (position.y > 0)
            velosity.add(0, GRAVITY, 0);
        velosity.scl(dt);
        position.add(MOVEMENT * dt, velosity.y, 0);
        if (position.y < mainClass.HEIGHT/3)
            position.y = mainClass.HEIGHT/3;

        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);


    }
    public void jump(){
        velosity.y = 250;
    }

    public Rectangle getBounds(){
        return bounds;
    }

}