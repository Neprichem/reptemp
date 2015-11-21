package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.mainClass;

/**
 * Created by Danil on 21.11.2015.
 */
public class Bullet {
    private static final int MOVEMENT = 300;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;

    private Texture bullet;

    public Bullet(int x, int y){

    position = new Vector3(x, y, 0);
    velosity = new Vector3(0, 0, 0);
    bullet = new Texture("bullet.png");
    bounds = new Rectangle(x, y, bullet.getWidth(), bullet.getHeight());
    }

    public Bullet(Vector3 x_){
        position = new Vector3(x_.x, x_.y, 0);
        velosity = new Vector3(0, 0, 0);
        bullet = new Texture("bullet.png");
        bounds = new Rectangle(x_.x, x_.y, bullet.getWidth(), bullet.getHeight());
    }
    public Vector3 getPosition() {
        return position;
    }

    public Texture getBullet() {
        return bullet;
    }

    public void update(float dt){
        velosity.add(MOVEMENT, 0, 0);
        velosity.scl(dt);
        position.add(MOVEMENT * dt, velosity.y, 0);
        if (position.y < mainClass.HEIGHT/4)
            position.y = mainClass.HEIGHT/4;

        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }
    /*
    public void jump(){
        velosity.y = 250;
    }*/

    public Rectangle getBounds(){
        return bounds;
    }

}
