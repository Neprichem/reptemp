package ru.kemgem.states;

/**
 * Created by Danil on 21.11.2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.Enemy;
import ru.kemgem.sprites.Hero;

/**
 * Created by Vitaly on 06.11.2015.
 */
public class PlayState extends State {

    private static final int ENEMY_SPACING = 125;
    private static final int ENEMY_COUNT = 4;

    private Hero hero;
    private Texture bg;

   // private Array<Tube> tubes;
   private Array<Enemy> enemys;
    private Array<Bullet> bullets;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        hero = new Hero(120, mainClass.HEIGHT/4);
        camera.setToOrtho(false, mainClass.WIDTH, mainClass.HEIGHT);
        bg = new Texture("bg2.jpg");

      /*
        tubes = new Array<Tube>();

        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }*/
        enemys = new Array<Enemy>();
        for (int i = 0; i < ENEMY_COUNT; i++)
        {
            enemys.add(new Enemy(mainClass.WIDTH + i*Enemy.ENEMY_WIDTH));
        }

        bullets = new Array<Bullet>();
        for (int i = 0; i < ENEMY_COUNT; i++)
        {
            bullets.add(new Bullet(hero.getPosition()));
        }

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            //bullets.add(new Bullet(hero.getPosition()));
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

        for (Enemy enemy : enemys) {
            if (camera.position.x - (camera.viewportWidth / 2) > enemy.getPosEnemy().x + enemy.getEnemy().getWidth()) {
                enemy.reposition(enemy.getPosEnemy().x + mainClass.WIDTH + Enemy.ENEMY_WIDTH); //((Enemy.ENEMY_WIDTH + ENEMY_SPACING)
                        //* ENEMY_COUNT));
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
        sb.draw(hero.getHero(), hero.getPosition().x, hero.getPosition().y);
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