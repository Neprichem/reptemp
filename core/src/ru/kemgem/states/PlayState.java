package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.Enemy;
import ru.kemgem.sprites.Hero;

public class PlayState extends State {

    private static final int ENEMY_COUNT = 4;

    private Hero hero;
    private Texture bg;
    private Texture jump;

    private Array<Enemy> enemys;
    private Array<Bullet> bullets;

    Vector3 touchPos;

    long lastDropTime;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        touchPos = new Vector3();
        hero = new Hero(120, mainClass.HEIGHT/4);
        camera.setToOrtho(false, mainClass.WIDTH, mainClass.HEIGHT);
        bg = new Texture("bg2.jpg");
        jump = new Texture("ButtonJump.png");

        enemys = new Array<Enemy>();
        bullets = new Array<Bullet>();

        for (int i = 0; i < ENEMY_COUNT; i++)
        {
            enemys.add(new Enemy(mainClass.WIDTH + i*Enemy.ENEMY_WIDTH, 0));
        }

    }

    private void spawnEnemy(float x, float y) {
        Enemy enemy = new Enemy(x, y);
        enemys.add(enemy);
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

    @Override
    public void update(float dt) {
        handleInput();
        hero.update(dt);
        camera.position.x = hero.getPosition().x + 280;

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }

        Iterator<Bullet> iterBullet1 = bullets.iterator();
        while (iterBullet1.hasNext())
        {
            Bullet bullet = iterBullet1.next();
            if (bullet.getPosition().x > hero.getPosition().x + mainClass.WIDTH)
            {
                bullet.dispose();
                iterBullet1.remove();
            }
        }

        Iterator<Enemy> iterEnemy = enemys.iterator();
        Iterator<Bullet> iterBullet2 = bullets.iterator();

        while (iterEnemy.hasNext())
        {
            Enemy enemy = iterEnemy.next();
            if (camera.position.x - (camera.viewportWidth / 2) > enemy.getPosEnemy().x + enemy.getEnemy().getWidth())
            {
                gsm.set(new PlayState(gsm));
            }
            while (iterBullet2.hasNext())
            {
                Bullet bullet = iterBullet2.next();
                if (enemy.collides(bullet.getBounds()))
                {
                    enemy.dispose();
                    iterEnemy.remove();
                    iterBullet2.remove();
                }
            }
            if (enemy.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(jump, camera.position.x - (camera.viewportWidth / 2) + 15, 15, 94, 94);
        hero.drawHero(sb);

        for (Bullet bullet : bullets) {
            sb.draw(bullet.getBullet(), bullet.getPosition().x, bullet.getPosition().y);
        }

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnEnemy(hero.getPosition().x + mainClass.WIDTH, mainClass.HEIGHT/4);

        for (Enemy enemy : enemys) {
            sb.draw(enemy.getEnemy(), enemy.getPosEnemy().x, enemy.getPosEnemy().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {}
}