package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.kemgem.MainClass;
import ru.kemgem.sprites.Bullet;
import ru.kemgem.sprites.Font;
import ru.kemgem.sprites.Hero;
import ru.kemgem.sprites.barriers.GameScene;

/**
 * Created by Danil on 05.12.2015.
 */
public class FreeRunState extends State {

    BitmapFont font;

    private Hero hero;

    private Texture jump;

    Vector3 touchPos;
    private float posY;

    GameScene gS;

    Array<Bullet> bullets;

    private Font bg;

    public FreeRunState(GameStateManager gsm) {
        super(gsm);
        touchPos = new Vector3();
        hero = new Hero(120, MainClass.HEIGHT/4);
        camera.setToOrtho(false, MainClass.WIDTH, MainClass.HEIGHT);

        bg = new Font();

        jump = new Texture("ButtonJump.png");

        font = new BitmapFont();

        posY = MainClass.HEIGHT/4;

        bullets = new Array<Bullet>();
        gS = new GameScene(hero.getPosition().x);
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

        gS.collidesSceneToHero(hero, camera);
        posY = gS.collidesBarriersForObject(hero, camera);
        gS.collidesSceneToBullets(bullets);
        gS.update(dt, camera.position.x - (camera.viewportWidth / 2), hero);

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }

        hero.update(dt, posY);
        camera.position.x = hero.getPosition().x + 280;

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        bg.drawFont(sb, camera.position.x - (camera.viewportWidth / 2));

        font.draw(sb, "Drops Collected: " + MainClass.score, camera.position.x +
                (camera.viewportWidth / 2) - 130, MainClass.HEIGHT - 24);

        sb.draw(jump, camera.position.x - (camera.viewportWidth / 2) + 15, 15, 94, 94);

        //gS.drawScene(sb,  camera.position.x);
        gS.drawScene(sb);

        for (Bullet bullet : bullets) {
            sb.draw(bullet.getBullet(), bullet.getPosition().x, bullet.getPosition().y);
        }

        hero.drawHero(sb);
        hero.drawHeroLive(sb, camera.position.x - (camera.viewportWidth / 2));

        sb.end();
    }

    @Override
    public void dispose() {

        bg.dispose();
        hero.dispose();
        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
        gS.dispose();
    }
}
