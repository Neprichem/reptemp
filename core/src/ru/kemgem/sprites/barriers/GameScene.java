package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import ru.kemgem.MainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.EnemyBullet;
import ru.kemgem.sprites.Hero;

/**
 * Created by Danil on 05.12.2015.
 */

//Игровой этоп уровня
public class GameScene {

    private Array<Swordsman> swordsmans;
    private Array<Shooter> shooters;
    private Array<EnemyBullet> enemyBullets;
    private Array<HighEnemy> highEnemies;
    private Array<StateBarriers> stateBarriers;

    private float length;
    private float posY;
    private float posX;

    long lastDropTime;


    public GameScene(float x)
    {
        posY = MainClass.HEIGHT/4;

        swordsmans = new Array<Swordsman>();
        shooters = new Array<Shooter>();
        enemyBullets = new Array<EnemyBullet>();
        highEnemies = new Array<HighEnemy>();
        stateBarriers = new Array<StateBarriers>();

        //длина сцены по координатам Х
        length = x + MainClass.WIDTH * 2;
        posX = length ;

        lastDropTime = TimeUtils.nanoTime();

        //Инициализация сцены, расстановка объектов
        initScene(x);
    }


    public void initScene(float x)
    {
        //Уделить внимание
        //удаление всех элементов прошой сцены

        //Задание координат на прямую
        stateBarriers.add(new StateBarriers(new Vector3(x + MainClass.WIDTH, MainClass.HEIGHT/4, 0),0));
        highEnemies.add(new HighEnemy(new Vector3(x + MainClass.WIDTH + 200, MainClass.HEIGHT/4, 0),0));
        highEnemies.add(new HighEnemy(new Vector3(x + MainClass.WIDTH + 600, MainClass.HEIGHT/4, 0),0));
        highEnemies.add(new HighEnemy(new Vector3(x + MainClass.WIDTH + 900, MainClass.HEIGHT/4, 0),0));
        swordsmans.add(new Swordsman(new Vector3(x + MainClass.WIDTH * 2, MainClass.HEIGHT/4, 0)));
        swordsmans.add(new Swordsman(new Vector3(x + MainClass.WIDTH * 2 + 300, MainClass.HEIGHT / 4, 0)));
    }

    public void collidesSceneToHero(Hero hero, OrthographicCamera camera)
    {

        Iterator<Swordsman> itsw = swordsmans.iterator();
        Iterator<Shooter> itsh = shooters.iterator();
        Iterator<HighEnemy> ithe = highEnemies.iterator();

        while (itsw.hasNext())
        {
            Swordsman swordsman = itsw.next();
            if (camera.position.x - (camera.viewportWidth / 2) > swordsman.getPosition().x + swordsman.getTexture().getWidth())
            {
                hero.death();
                swordsman.dispose();
                itsw.remove();
            }

            if (swordsman.collides(hero.getBounds())){
                hero.death();
                swordsman.dispose();
                itsw.remove();
            }
        }

        while (itsh.hasNext())
        {
            Shooter sh = itsh.next();
            if (camera.position.x - (camera.viewportWidth / 2) > sh.getPosition().x + sh.getTexture().getWidth())
            {
                hero.death();
                sh.dispose();
                itsh.remove();
            }

            if (sh.collides(hero.getBounds())) {
                hero.death();
                sh.dispose();
                itsh.remove();
            }
        }
        while (ithe.hasNext())
        {
            HighEnemy he = ithe.next();
            if (camera.position.x - (camera.viewportWidth / 2) > he.getPosition().x + he.getTexture().getWidth())
            {
                he.dispose();
                ithe.remove();
            }

            if (he.collides(hero.getBounds()) && he.getLive()) {
                he.death();
                hero.death();
            }

        }
    }

    public void collidesSceneToBullets(Array<Bullet> bullets)
    {
        Iterator<Bullet> itb = bullets.iterator();
            while (itb.hasNext())
            {
                Bullet b = itb.next();
                if (b.collidesSwordsman(swordsmans) || b.collidesShooter(shooters) || b.collidesHighEnemy(highEnemies)
                        || b.collidesBarriers(stateBarriers))
                {
                    b.dispose();
                    itb.remove();
                }
            }
    }

    public void collidesBulletsToHero(Hero hero)
    {
        Iterator<EnemyBullet> iteb = enemyBullets.iterator();
        while (iteb.hasNext())
        {
            EnemyBullet bullet = iteb.next();
            if (hero.collides(bullet.getBounds()))
            {
                hero.death();
                bullet.dispose();
                iteb.remove();
            }
        }
    }

    public void update(float dt, float x, Hero hero)
    {
        if (x > posX)
        {
            posX += length;
            initScene(x);
        }

        collidesBulletsToHero(hero);

        for (Swordsman sw: swordsmans) sw.update(dt);

        for (EnemyBullet enemybullet : enemyBullets) {
            enemybullet.update(dt);
        }

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000 ) {

            for (Shooter shooter : shooters) {
                shooter.shot(enemyBullets, hero);
                lastDropTime = TimeUtils.nanoTime();
            }

            for (HighEnemy shooter : highEnemies) {
                shooter.getShooter().shot(enemyBullets, hero);
                lastDropTime = TimeUtils.nanoTime();
            }
        }
    }

    public void drawScene(SpriteBatch sb)
    {
        for (StateBarriers sBarriers : stateBarriers) {
            sb.draw(sBarriers.getTexture(), sBarriers.getPosition().x, sBarriers.getPosition().y);
        }
        for (HighEnemy he : highEnemies) {
            he.drawHighEnemy(sb);
        }
        for (EnemyBullet eb : enemyBullets) {
            sb.draw(eb.getBullet(), eb.getPosition().x, eb.getPosition().y);
        }
        for (Shooter sh : shooters) {
            sh.drawShooter(sb);
        }
        for (Swordsman swordsman : swordsmans) {
            sb.draw(swordsman.getTexture(), swordsman.getPosition().x, swordsman.getPosition().y);
        }
    }

    public void dispose()
    {
        for (HighEnemy he : highEnemies) {
            he.dispose();
        }
        for (EnemyBullet eb : enemyBullets) {
            eb.dispose();
        }
        for (Shooter sh : shooters) {
            sh.dispose();
        }
        for (Swordsman swordsman : swordsmans) {
            swordsman.dispose();
        }
        for (StateBarriers sBarriers : stateBarriers) {
            sBarriers.dispose();
        }
    }
}
