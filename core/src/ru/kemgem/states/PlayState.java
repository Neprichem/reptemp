package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.EnemyBullet;
import ru.kemgem.sprites.Hero;
import ru.kemgem.sprites.barriers.Enemy;
import ru.kemgem.sprites.barriers.Shooter;
import ru.kemgem.sprites.barriers.Swordsman;

public class PlayState extends State {

    private static final int ENEMY_COUNT = 4;

    BitmapFont font;
    int dropsGatchered;

    private Hero hero;
    private Texture bg;
    private Texture jump;

    private Array<Swordsman> swordsmans;
    private Array<Shooter> shooters;
    private Array<Bullet> bullets;
    private Array<EnemyBullet> enemyBullets;

    Vector3 touchPos;

    long lastDropTime;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        touchPos = new Vector3();
        hero = new Hero(120, mainClass.HEIGHT/4);
        camera.setToOrtho(false, mainClass.WIDTH, mainClass.HEIGHT);
        bg = new Texture("bg2.jpg");
        jump = new Texture("ButtonJump.png");

        font = new BitmapFont();

        swordsmans = new Array<Swordsman>();
        bullets = new Array<Bullet>();
        shooters = new Array<Shooter>();
        enemyBullets = new Array<EnemyBullet>();

        for (int i = 0; i < ENEMY_COUNT; i++)
        {
            swordsmans.add(new Swordsman(mainClass.WIDTH + i*Enemy.ENEMY_WIDTH, mainClass.HEIGHT/4));
        }

    }

    private void spawnEnemy(float x, float y) {
        Swordsman enemy = new Swordsman(x, y);
        swordsmans.add(enemy);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnShooter(float x, float y) {
        Shooter sh = new Shooter(x, y);
        shooters.add(sh);
        lastDropTime = TimeUtils.nanoTime();
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x >= camera.position.x - (camera.viewportWidth / 2) + 15 &&
                    touchPos.x <= camera.position.x - (camera.viewportWidth / 2) + jump.getWidth() - 15&&
                    touchPos.y >= 15 && touchPos.y <= jump.getHeight() - 15)
            {
                hero.jump();
            }
            else if (touchPos.x >= hero.getPosition().x)
            {
                bullets.add(new Bullet(hero.getPosition(), touchPos.x, touchPos.y));
            }

        }
    }

    private void borderMoveBullets()
    {
        Iterator<Bullet> iterBullet = bullets.iterator();
        while (iterBullet.hasNext())
        {
            Bullet bullet = iterBullet.next();
            if (bullet.getPosition().x > hero.getPosition().x + mainClass.WIDTH)
            {
                bullet.dispose();
                iterBullet.remove();
            }
        }
        Iterator<EnemyBullet> eniterBullet = enemyBullets.iterator();
        while (eniterBullet.hasNext())
        {
            EnemyBullet eb = eniterBullet.next();
            if (eb.getPosition().x > hero.getPosition().x + mainClass.WIDTH ||
                    eb.getPosition().x < hero.getPosition().x - mainClass.WIDTH)
            {
                eb.dispose();
                eniterBullet.remove();
            }
        }
    }

    //меттод обработки коллизий

    //на переработку
    //-------------------------------------------------------------------------------------------------------------------------------
    private void collides()
    {
        Iterator<Swordsman> iterEnemy = swordsmans.iterator();
        Iterator<Bullet> iterBullet = bullets.iterator();
        Iterator<EnemyBullet> ieb = enemyBullets.iterator();
        Iterator<Shooter> is = shooters.iterator();

        while (ieb.hasNext())
        {
            EnemyBullet bullet = ieb.next();
            if (hero.collides(bullet.getBounds()))
            {
               // gsm.set(new PlayState(gsm));
            }
        }

        while (iterEnemy.hasNext())
        {
            Swordsman swordsman = iterEnemy.next();
            if (camera.position.x - (camera.viewportWidth / 2) > swordsman.getPosition().x + swordsman.getTexture().getWidth())
            {
                gsm.set(new PlayState(gsm));
            }
            while (iterBullet.hasNext())
            {
                Bullet bullet = iterBullet.next();
                if (swordsman.collides(bullet.getBounds()))
                {
                    swordsman.dispose();
                    iterEnemy.remove();
                    iterBullet.remove();
                    dropsGatchered++;
                }
            }
            if (swordsman.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        //------------------------------------------------------------------------------------------------------------
        while (is.hasNext())
        {
            Shooter sh = is.next();
            if (camera.position.x - (camera.viewportWidth / 2) > sh.getPosition().x + sh.getTexture().getWidth())
            {
                gsm.set(new PlayState(gsm));
            }
            while (iterBullet.hasNext())
            {
                Bullet bullet = iterBullet.next();
                if (sh.collides(bullet.getBounds()))
                {
                    sh.dispose();
                    is.remove();
                    iterBullet.remove();
                    dropsGatchered++;
                }
            }
            if (sh.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        //-------------------------------------------------------------------------------------------------------------
    }

    @Override
    public void update(float dt) {
        handleInput();
        hero.update(dt);
        camera.position.x = hero.getPosition().x + 280;

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }

        for (EnemyBullet enemybullet : enemyBullets) {
            enemybullet.update(dt);
        }

        borderMoveBullets();
        collides();

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        font.draw(sb, "Drops Collected: " + dropsGatchered,  camera.position.x - (camera.viewportWidth / 2) + 30, mainClass.HEIGHT - 30);
        sb.draw(jump, camera.position.x - (camera.viewportWidth / 2) + 15, 15, 94, 94);
        hero.drawHero(sb);

        for (Bullet bullet : bullets) {
            sb.draw(bullet.getBullet(), bullet.getPosition().x, bullet.getPosition().y);
        }

        for (EnemyBullet eb : enemyBullets) {
            sb.draw(eb.getBullet(), eb.getPosition().x, eb.getPosition().y);
        }

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
        {
            spawnShooter(hero.getPosition().x + mainClass.WIDTH + 50, mainClass.HEIGHT / 4);
            spawnEnemy(hero.getPosition().x + mainClass.WIDTH, mainClass.HEIGHT/4);
            for (Shooter shooter : shooters) {
                shooter.shot(enemyBullets, hero);
            }
        }


        for (Swordsman swordsman : swordsmans) {
            sb.draw(swordsman.getTexture(), swordsman.getPosition().x, swordsman.getPosition().y);
        }
        for (Shooter sh : shooters) {
            sb.draw(sh.getTexture(), sh.getPosition().x, sh.getPosition().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {}
}