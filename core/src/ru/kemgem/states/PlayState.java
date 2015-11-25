package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.EnemyBullet;
import ru.kemgem.sprites.Hero;
import ru.kemgem.sprites.barriers.Enemy;
import ru.kemgem.sprites.barriers.HighEnemy;
import ru.kemgem.sprites.barriers.Shooter;
import ru.kemgem.sprites.barriers.Swordsman;

public class PlayState extends State {

    private static final int ENEMY_COUNT = 4;
    private static final int FRAME_COLS = 3; // #1
    private static final int FRAME_ROWS = 1; // #2

    BitmapFont font;
    int dropsGatchered;

    private Hero hero;

    private Texture jump;

    private Array<Swordsman> swordsmans;
    private Array<Shooter> shooters;
    private Array<Bullet> bullets;
    private Array<EnemyBullet> enemyBullets;
    private Array<HighEnemy> highEnemies;

    Vector3 touchPos;

    long lastDropTime;

    Animation bgAnimation;
    private Texture bg;
    TextureRegion[] bgFrames;
    SpriteBatch spriteBatch;
    TextureRegion currentFrame;

    private int rand;

    float stateTime;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        touchPos = new Vector3();
        hero = new Hero(120, mainClass.HEIGHT/4);
        camera.setToOrtho(false, mainClass.WIDTH, mainClass.HEIGHT);

        rand  = MathUtils.random(0, 5);

        bg = new Texture("bgf_1-3_2.jpg");
        TextureRegion[][] tmp = TextureRegion.split(bg, bg.getWidth()/FRAME_COLS, bg.getHeight()/FRAME_ROWS); // #10

        bgFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                bgFrames[index++] = tmp[i][j];
            }
        }
        bgAnimation = new Animation(0.150f, bgFrames); // #11
        spriteBatch = new SpriteBatch(); // #12
        stateTime = 0f; // #13

        jump = new Texture("ButtonJump.png");

        font = new BitmapFont();

        swordsmans = new Array<Swordsman>();
        bullets = new Array<Bullet>();
        shooters = new Array<Shooter>();
        enemyBullets = new Array<EnemyBullet>();
        highEnemies = new Array<HighEnemy>();

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

    private void spawnShooter(float x, float y, int type) {
        Shooter sh = new Shooter(x, y, type);
        shooters.add(sh);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnHighEnemy(float x, float y, int type) {
        highEnemies.add(new HighEnemy(new Vector3(x, y, 0), type));
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

    //методы обработки коллизий
//--------------------------------------------------------------------------------------------------
    private void collidesBullet()
    {
        Iterator<Bullet> itb = bullets.iterator();
        Iterator<EnemyBullet> iteb = enemyBullets.iterator();
        while (itb.hasNext())
        {
            Bullet b = itb.next();
            if (b.collidesSwordsman(swordsmans) || b.collidesShooter(shooters) || b.collidesHighEnemy(highEnemies))
            {
                b.dispose();
                itb.remove();
                dropsGatchered++;
            }
        }
        while (iteb.hasNext())
        {
            EnemyBullet bullet = iteb.next();
            if (hero.collides(bullet.getBounds()))
            {
               // hero.death();
                bullet.dispose();
                iteb.remove();
            }
        }

    }

    private void collidesEnemy() {
        Iterator<Swordsman> itsw = swordsmans.iterator();
        Iterator<Shooter> itsh = shooters.iterator();

        while (itsw.hasNext())
        {
            Swordsman swordsman = itsw.next();
            if (camera.position.x - (camera.viewportWidth / 2) > swordsman.getPosition().x + swordsman.getTexture().getWidth())
            {
                gsm.set(new PlayState(gsm));
            }

            if (swordsman.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
        }

        while (itsh.hasNext())
        {
            Shooter sh = itsh.next();
            if (camera.position.x - (camera.viewportWidth / 2) > sh.getPosition().x + sh.getTexture().getWidth())
            {
                gsm.set(new PlayState(gsm));
            }

            if (sh.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
        }
    }
//----------------------------------------------------------------------------------------------------------------
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
        collidesBullet();
        collidesEnemy();

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // #15
        currentFrame = bgAnimation.getKeyFrame(stateTime, true); // #16
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(currentFrame, camera.position.x - (camera.viewportWidth / 2), 0,
                camera.position.x + (camera.viewportWidth / 2), mainClass.HEIGHT);
        font.draw(sb, "Drops Collected: " + dropsGatchered,  camera.position.x +
                (camera.viewportWidth / 2) - 130, mainClass.HEIGHT - 24);
        sb.draw(jump, camera.position.x - (camera.viewportWidth / 2) + 15, 15, 94, 94);

        hero.drawHero(sb);
        hero.drawHeroLive(sb, camera.position.x - (camera.viewportWidth / 2));

        for (Bullet bullet : bullets) {
            sb.draw(bullet.getBullet(), bullet.getPosition().x, bullet.getPosition().y);
        }

        for (EnemyBullet eb : enemyBullets) {
            sb.draw(eb.getBullet(), eb.getPosition().x, eb.getPosition().y);
        }

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
        {
            switch (rand)
            {
                case 1:
                    spawnShooter(hero.getPosition().x + mainClass.WIDTH + 50, mainClass.HEIGHT / 4, 1);
                    break;
                case 0:
                    spawnEnemy(hero.getPosition().x + mainClass.WIDTH, mainClass.HEIGHT / 4);
                    break;
                case 2:
                    spawnShooter(hero.getPosition().x + mainClass.WIDTH + 50, mainClass.HEIGHT / 4, 2);
                    break;
                case 3:
                    spawnHighEnemy(hero.getPosition().x + mainClass.WIDTH + 50, mainClass.HEIGHT / 4, 1);
                    break;
                case 4:
                    spawnHighEnemy(hero.getPosition().x + mainClass.WIDTH + 50, mainClass.HEIGHT / 4, 2);
                    break;
            }
            for (Shooter shooter : shooters) {
                shooter.shot(enemyBullets, hero);
            }
        }

        for (Swordsman swordsman : swordsmans) {
            sb.draw(swordsman.getTexture(), swordsman.getPosition().x, swordsman.getPosition().y);
        }
        for (Shooter sh : shooters) {
           // sb.draw(sh.getTexture(), sh.getPosition().x, sh.getPosition().y);
            sh.drawShooter(sb);
        }
        for (HighEnemy he : highEnemies) {
            // sb.draw(sh.getTexture(), sh.getPosition().x, sh.getPosition().y);
            he.drawHighEnemy(sb);
        }

        sb.end();
        rand = 4;
       // rand  = MathUtils.random(0, 5);

    }

    @Override
    public void dispose() {}
}