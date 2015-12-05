package ru.kemgem.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.MainClass;

/**
 * Created by Danil on 04.12.2015.
 */
public class ChangeGameState extends State {

    private Texture background;
    private Texture btnHard, btnFreeRun, btnBack;
    Vector3 touchPos;

    public ChangeGameState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg5.png");
        btnFreeRun = new Texture("btnFreeRun.png");
        btnHard = new Texture("btnHard.png");
        btnBack = new Texture("btnBack.png");

        touchPos = new Vector3();
        camera.setToOrtho(false, MainClass.WIDTH, MainClass.HEIGHT);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x > MainClass.WIDTH  - btnFreeRun.getWidth() - 15 &&  touchPos.x < MainClass.WIDTH - 15
                    && touchPos.y > (MainClass.HEIGHT/2) && touchPos.y < (MainClass.HEIGHT/2) + btnFreeRun.getHeight()) {
                dispose();
                MainClass.score = 0;
                MainClass.typeGame = 1;
                gsm.set(new FreeRunState(gsm));
            }

            if (touchPos.x > MainClass.WIDTH  - btnHard.getWidth() - 15 &&  touchPos.x < MainClass.WIDTH - 15
                    && touchPos.y > MainClass.HEIGHT / 2 - btnFreeRun.getHeight() - 15 &&
                    touchPos.y < (MainClass.HEIGHT/2) - 15 + btnHard.getHeight() - btnFreeRun.getHeight()) {
                dispose();
                MainClass.score = 0;
                MainClass.typeGame = 2;
                gsm.set(new PlayState(gsm));
            }

            if (touchPos.x > MainClass.WIDTH  - btnBack.getWidth() - 15 &&  touchPos.x < MainClass.WIDTH - 15
                    && touchPos.y > MainClass.HEIGHT / 2 - btnHard.getHeight() - btnFreeRun.getHeight() - 30 &&
                    touchPos.y < (MainClass.HEIGHT/2) - 30 + btnBack.getHeight() - btnHard.getHeight() - btnFreeRun.getHeight()) {
                dispose();
                gsm.set(new MenuState(gsm));
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
        sb.draw(btnFreeRun, MainClass.WIDTH  - btnFreeRun.getWidth() - 15, MainClass.HEIGHT / 2);
        sb.draw(btnHard, MainClass.WIDTH  - btnHard.getWidth() - 15,
                MainClass.HEIGHT / 2 - btnFreeRun.getHeight() - 15);
        sb.draw(btnBack, MainClass.WIDTH  - btnBack.getWidth() - 15,
                MainClass.HEIGHT / 2 - btnHard.getHeight() - btnFreeRun.getHeight() - 30);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        btnFreeRun.dispose();
        btnHard.dispose();
        btnBack.dispose();
    }
}