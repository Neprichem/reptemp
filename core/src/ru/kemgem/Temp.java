package ru.kemgem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.kemgem.sprites.Bullet;

/**
 * Created by Danil on 23.11.2015.
 */
public class Temp extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    Vector3 touchPos;

    private Array<Bullet> bullets;
    protected OrthographicCamera camera;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();
        bullets = new Array<Bullet>();
    }

    private void handleInput() {
        if (Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            //bucket.x = (int) (touchPos.x -64 / 2);
            //bullets.add(new Bullet(hero.getPosition(), Gdx.input.getX(),Gdx.input.getY()));
           // bullets.add(new Bullet( touchPos.x , touchPos.y ));
        }

    }
    @Override
    public void render () {

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        batch.begin();
        batch.draw(img, 0, 0);
        for (Bullet bullet : bullets) {
            batch.draw(bullet.getBullet(), bullet.getPosition().x, bullet.getPosition().y);
        }
        batch.end();

    }
}
