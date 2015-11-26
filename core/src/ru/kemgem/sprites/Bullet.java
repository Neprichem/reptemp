package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import ru.kemgem.sprites.barriers.HighEnemy;
import ru.kemgem.sprites.barriers.Shooter;
import ru.kemgem.sprites.barriers.Swordsman;

/**
 * Created by Danil on 21.11.2015.
 */
public class Bullet {
    private static final int MOVEMENT = 15; // скорость

    private Vector3 position;  // позиция выстрела
    private Vector3 velosity;// вектор скорости

    private Rectangle bounds; // квадратная проекция
    private float angle;//угол
    private Texture bullet;

  //  Sound enemyDis = Gdx.audio.newSound(Gdx.files.internal("data/Explosion14.wav"));

    //конструктор класса, на вход координаты начала полета
    //и координаты конечнего направления
    public Bullet(Vector3 X, float _x, float _y)
    {
        //заполнение координаь
        position = new Vector3(X.x, X.y, 0);
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

    public boolean collidesSwordsman(Array<Swordsman> enemys)
    {
        Iterator<Swordsman> itsw = enemys.iterator();

        while (itsw.hasNext())
        {
            Swordsman swordsman = itsw.next();
            if (swordsman.collides(bounds))
            {
                swordsman.dispose();
                itsw.remove();
                return true;
            }
        }
        return false;
    }

    public boolean collidesShooter(Array<Shooter> enemys)
    {
        Iterator<Shooter> itsh = enemys.iterator();

        while (itsh.hasNext())
        {
            Shooter sh = itsh.next();
            if (sh.collides(bounds))
            {
                sh.dispose();
                itsh.remove();
                return true;
            }
        }
        return false;
    }

    public boolean collidesHighEnemy(Array<HighEnemy> enemys)
    {
        Iterator<HighEnemy> ithe = enemys.iterator();

        while (ithe.hasNext())
        {
            HighEnemy he = ithe.next();
            if (he.getLive())
            if (he.getShooter().collides(bounds))
            {
                he.death();
               // ithe.remove();
              //  enemyDis.play(1.0f);
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        bullet.dispose();
        //enemyDis.dispose();
    }
}
