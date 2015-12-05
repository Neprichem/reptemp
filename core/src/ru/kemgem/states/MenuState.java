package ru.kemgem.states;

/**
 * Created by Danil on 21.11.2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.MainClass;

/**
 * Created by Vitaly on 28.10.2015.
 */
public class MenuState extends State {

    private Texture background;
    private Texture btnPlay, btnScore, btnExit;
    Vector3 touchPos;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg5.png");
        btnPlay = new Texture("btnPlay.png");
        btnScore = new Texture("btnScore.png");
        btnExit = new Texture("btnExit.png");

        touchPos = new Vector3();
        camera.setToOrtho(false, MainClass.WIDTH, MainClass.HEIGHT);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x > MainClass.WIDTH  - btnPlay.getWidth() - 15 &&  touchPos.x < MainClass.WIDTH - 15
                    && touchPos.y > (MainClass.HEIGHT/2) && touchPos.y < (MainClass.HEIGHT/2) + btnPlay.getHeight()) {
                gsm.set(new ChangeGameState(gsm));
            }

            if (touchPos.x > MainClass.WIDTH  - btnScore.getWidth() - 15 &&  touchPos.x < MainClass.WIDTH - 15
                    && touchPos.y > MainClass.HEIGHT / 2 - btnPlay.getHeight() - 15 &&
                    touchPos.y < (MainClass.HEIGHT/2) - 15 + btnScore.getHeight() - btnPlay.getHeight())
                gsm.set(new ScoreState(gsm));

            if (touchPos.x > MainClass.WIDTH  - btnExit.getWidth() - 15 &&  touchPos.x < MainClass.WIDTH - 15
                    && touchPos.y > MainClass.HEIGHT / 2 - btnScore.getHeight() - btnPlay.getHeight() - 30 &&
                    touchPos.y < (MainClass.HEIGHT/2) - 30 + btnExit.getHeight() - btnScore.getHeight() - btnPlay.getHeight()) {
                dispose();
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, MainClass.WIDTH, MainClass.HEIGHT);
        sb.draw(btnPlay, MainClass.WIDTH  - btnPlay.getWidth() - 15, MainClass.HEIGHT / 2);
        sb.draw(btnScore, MainClass.WIDTH  - btnScore.getWidth() - 15,
                MainClass.HEIGHT / 2 - btnPlay.getHeight() - 15);
        sb.draw(btnExit, MainClass.WIDTH  - btnExit.getWidth() - 15,
                MainClass.HEIGHT / 2 - btnScore.getHeight() - btnPlay.getHeight() - 30);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        btnPlay.dispose();
        btnScore.dispose();
        btnExit.dispose();
    }
}