package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.MainClass;

/**
 * Created by Danil on 28.11.2015.
 */
public class ScoreState  extends State {

    private Texture background;
    private BitmapFont font;
    private Texture menu;
    Vector3 touchPos;

    public ScoreState(GameStateManager gsm){
        super(gsm);
        touchPos = new Vector3();
        camera.setToOrtho(false, MainClass.WIDTH, MainClass.HEIGHT);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        background = new Texture("bg5.png");

        menu = new Texture("btnMenu.png");

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x > camera.position.x - menu.getWidth()/2 &&  touchPos.x < camera.position.x
                    + menu.getWidth()/2 && touchPos.y > menu.getHeight() + 50 &&
                    touchPos.y < menu.getHeight()*2 + 50) {
                MainClass.gsm.push(new MenuState(MainClass.gsm));
            }
        }
    }

    @Override
    public void update(float dt) { handleInput(); }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0, camera.position.x +
                (camera.viewportWidth / 2), MainClass.HEIGHT);

        font.draw(sb, "Your last result: " + MainClass.score, camera.position.x - font.getSpaceWidth() - 50, MainClass.HEIGHT - 48);

        sb.draw(menu, camera.position.x - menu.getWidth()/2, menu.getHeight() + 50);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        menu.dispose();
        font.dispose();
    }
}

