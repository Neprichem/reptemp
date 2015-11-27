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
 * Created by Danil on 27.11.2015.
 */
public class GameOver extends State {

    private Texture background;
    private BitmapFont font;
    private Texture ng, menu;
    Vector3 touchPos;

    public GameOver(GameStateManager gsm){
        super(gsm);
        touchPos = new Vector3();
        camera.setToOrtho(false, MainClass.WIDTH, MainClass.HEIGHT);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        background = new Texture("bg5.png");

        ng = new Texture("btnRepeat.png");
        menu = new Texture("btnMenu.png");

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x > camera.position.x - ng.getWidth()/2 &&  touchPos.x < camera.position.x + ng.getWidth()/2
            && touchPos.y > MainClass.HEIGHT/2 && touchPos.y < MainClass.HEIGHT/2 + ng.getHeight()) {
                MainClass.gsm.push(new PlayState(MainClass.gsm));
                MainClass.score = 0;
            }
            if (touchPos.x > camera.position.x - menu.getWidth()/2 &&  touchPos.x < camera.position.x
                    + menu.getWidth()/2 && touchPos.y > MainClass.HEIGHT/2 - ng.getHeight() -15 &&
                    touchPos.y < MainClass.HEIGHT/2 + menu.getHeight() - ng.getHeight() - 15) {
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
        font.draw(sb, "Game Over", camera.position.x - font.getSpaceWidth() - 50, MainClass.HEIGHT - 24);
        font.draw(sb, "Your result: " + MainClass.score, camera.position.x - font.getSpaceWidth() - 50, MainClass.HEIGHT - 48);

        sb.draw(ng, camera.position.x - ng.getWidth()/2, MainClass.HEIGHT/2 );
        sb.draw(menu, camera.position.x - menu.getWidth()/2, MainClass.HEIGHT/2 - ng.getHeight() - 15);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ng.dispose();
        menu.dispose();
        font.dispose();
    }
}
