package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danil on 24.11.2015.
 */
public class EnemyBullet {
    private static final int MOVEMENT = -15; // скорость

    private Vector3 position;  // позиция выстрела
    private Vector3 velosity;// вектор скорости

    private Rectangle bounds; // квадратная проекция
    private float angle;//угол
    private Texture bullet;

    //конструктор класса, на вход координаты начала полета
    //и координаты конечнего направления
    public EnemyBullet(Vector3 X, float _x, float _y)
    {
        //заполнение координаt
        position = new Vector3(X);
        velosity = new Vector3(0, 0, 0);

        bullet = new Texture("bullet.png");//текстура

        bounds = new Rectangle(X.x, X.y, bullet.getWidth(), bullet.getHeight());//проекция

        //угол, для указания направления полета снаряда
        angle = (float) Math.atan((double)(position.y - _y) / (position.x - _x));
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getBullet() {
        return bullet;
    }

    //обработчик движения
    public void update(float dt)
    {
        //изменение координат
        velosity.add(MOVEMENT * MathUtils.cos(angle), MOVEMENT * MathUtils.sin(angle), 0);
        velosity.scl(dt);
        position.add(velosity.x, velosity.y, 0);
        velosity.scl(1 / dt);
        //присвоение координат проекции
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose() { bullet.dispose(); }
}