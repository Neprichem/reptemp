package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.Enemy;
import ru.kemgem.sprites.Hero;

/**
 * Created by Vitaly on 06.11.2015.
 */
/**
 * Changed by Danil on 21.11.2015.
 */
public class PlayState extends State {

    private static final int ENEMY_COUNT = 4;

    private Hero hero;
    private Texture bg;

    private Array<Enemy> enemys;
    private Array<Bullet> bullets;

    Vector3 touchPos;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        touchPos = new Vector3();
        hero = new Hero(120, mainClass.HEIGHT/4);
        camera.setToOrtho(false, mainClass.WIDTH, mainClass.HEIGHT);
        bg = new Texture("bg2.jpg");
        /*
        tubes = new Array<Tube>();

        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }*/
        enemys = new Array<Enemy>();
        bullets = new Array<Bullet>();

        for (int i = 0; i < ENEMY_COUNT; i++)
        {
            enemys.add(new Enemy(mainClass.WIDTH + i*Enemy.ENEMY_WIDTH));
        }

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            //bucket.x = (int) (touchPos.x -64 / 2);
            //bullets.add(new Bullet(hero.getPosition(), Gdx.input.getX(),Gdx.input.getY()));
           // bullets.add(new Bullet( touchPos.x , touchPos.y ));
            bullets.add(new Bullet(hero.getPosition(),touchPos.x,touchPos.y));
           // bullets.add(new Bullet( Gdx.input.getX(),Gdx.input.getY()));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        hero.update(dt);
        camera.position.x = hero.getPosition().x + 280;

        /*
        for (Tube tube : tubes){
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        */

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }
/*
       Iterator<Rectangle> iter = raindrops.iterator();
      while (iter.hasNext()){
         Rectangle raindrop = iter.next();
         raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
         if (raindrop.y + 64 < 0) iter.remove();
         if (raindrop.overlaps(bucket)){
            dropSound.play();
            iter.remove();
         }
  */
        Iterator<Enemy> iterEnemy = enemys.iterator();
        Iterator<Bullet> iterBullet = bullets.iterator();
        while (iterEnemy.hasNext()){
            Enemy enemy = iterEnemy.next();

            if (camera.position.x - (camera.viewportWidth / 2) > enemy.getPosEnemy().x + enemy.getEnemy().getWidth()) {
                enemy.reposition(enemy.getPosEnemy().x + mainClass.WIDTH + Enemy.ENEMY_WIDTH); //((Enemy.ENEMY_WIDTH + ENEMY_SPACING)
                //* ENEMY_COUNT));
            }
            while (iterBullet.hasNext()) {
                Bullet bullet = iterBullet.next();
                if (enemy.collides(bullet.getBounds())) {
                    enemy.dispose();
                    iterEnemy.remove();
                    iterBullet.remove();
                }
            }
            if (enemy.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
            }


        /*

        for (Enemy enemy : enemys) {
            if (camera.position.x - (camera.viewportWidth / 2) > enemy.getPosEnemy().x + enemy.getEnemy().getWidth()) {
                enemy.reposition(enemy.getPosEnemy().x + mainClass.WIDTH + Enemy.ENEMY_WIDTH); //((Enemy.ENEMY_WIDTH + ENEMY_SPACING)
                        //* ENEMY_COUNT));
            }
            for (Bullet bullet : bullets) {
                if (enemy.collides(bullet.getBounds())) {
                    enemy.dispose();
                    //gsm.set(new PlayState(gsm));
                }

            }

            if (enemy.collides(hero.getBounds()))
                gsm.set(new PlayState(gsm));
        }*/
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        hero.drawHero(sb);
        //sb.draw(hero.getHeroRegion(), hero.getPosition().x, hero.getPosition().y);
        /*
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }*/
        for (Bullet bullet : bullets) {
            sb.draw(bullet.getBullet(), bullet.getPosition().x, bullet.getPosition().y);
        }

        for (Enemy enemy : enemys) {
            sb.draw(enemy.getEnemy(), enemy.getPosEnemy().x, enemy.getPosEnemy().y);
        }

        sb.end();

    }

    @Override
    public void dispose() {
    }
}