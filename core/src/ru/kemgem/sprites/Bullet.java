package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danil on 21.11.2015.
 */
public class Bullet {
    private static final int MOVEMENT = 15; // скорость

    private Vector3 position;  // позиция выстрела
    private Vector3 finishPosition; // финальное направление
    private Vector3 velosity;// вектор скорости

    private Rectangle bounds; // квадратная проекция

    private float angle;

    private Texture bullet;

    public Bullet(Vector3 X, float _x, float _y){
        position = new Vector3(X.x, X.y, 0);

        finishPosition = new Vector3(_x, _y, 0);

        velosity = new Vector3(0, 0, 0);

        bullet = new Texture("bullet.png");

        bounds = new Rectangle(X.x, X.y, bullet.getWidth(), bullet.getHeight());

        angle = (float) Math.atan((double)(position.y - _y) / (position.x - _x));
    }

    public Bullet(float _x, float _y){
        position = new Vector3(_x, _y, 0);

       // finishPosition = new Vector3(_x, _y, 0);

        velosity = new Vector3(0, 0, 0);

        bullet = new Texture("bullet.png");

        bounds = new Rectangle(_x, _y, bullet.getWidth(), bullet.getHeight());

        angle = (float) Math.atan((double)(position.y - _y) / (position.x - _x));
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getBullet() {
        return bullet;
    }

    public void update(float dt){


       // position.add(finishPosition.x, finishPosition.y, 0);
//        bounds.setPosition(position.x, position.y);

        velosity.add(MOVEMENT * MathUtils.cos(angle), MOVEMENT * MathUtils.sin(angle), 0);
        velosity.scl(dt);
        position.add(velosity.x, velosity.y, 0);
        //if (position.y < mainClass.HEIGHT/4)
           // position.y = mainClass.HEIGHT/4;

        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
        /*velosity.add(MOVEMENT, MOVEMENT, 0);
        velosity.scl(dt);
        position.add(MOVEMENT * dt * MathUtils.cos(1/4), MOVEMENT * dt * MathUtils.sin(1/4), 0);
        //position.add(MVEMENT * dt * MathUtils.cos(angle), MOVEMENT * dt * MathUtils.sin(angle), 0);
       // if (position.y < mainClass.HEIGHT/4)
         //   position.y = mainClass.HEIGHT/4;

        velosity.scl(1 / dt);

        position.x += MOVEMENT*dt* Math.cos(angle);         //движение по Х со скоростью mSpeed и углу заданном координатой angle
        position.y += MOVEMENT*dt    * Math.sin(angle);
        bounds.setPosition(position.x, position.y);*/
    }

    public Rectangle getBounds(){
        return bounds;
    }

}
