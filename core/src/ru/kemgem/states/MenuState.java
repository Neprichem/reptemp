package ru.kemgem.states;

/**
 * Created by Danil on 21.11.2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.kemgem.mainClass;

/**
 * Created by Vitaly on 28.10.2015.
 */
public class MenuState extends State {

    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, mainClass.WIDTH, mainClass.HEIGHT);
        sb.draw(playBtn, (mainClass.WIDTH / 2) - (playBtn.getWidth() / 2), mainClass.HEIGHT / 2);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();

    }
}