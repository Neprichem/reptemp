package ru.kemgem.states;

/**
 * Created by Danil on 21.11.2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.Enemy;
import ru.kemgem.sprites.Hero;

/**
 * Created by Vitaly on 06.11.2015.
 */
public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Hero bird;
    private Texture bg;

   // private Array<Tube> tubes;
   private Array<Enemy> enemys;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Hero(0, 75);
        camera.setToOrtho(false, mainClass.WIDTH, mainClass.HEIGHT);
        bg = new Texture("bg2.jpg");

      /*
        tubes = new Array<Tube>();

        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }*/
        enemys = new Array<Enemy>();

        for (int i = 0; i < TUBE_COUNT; i++) {
            enemys.add(new Enemy(i * (TUBE_SPACING + Enemy.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            bird.jump();

    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        /*
        for (Tube tube : tubes){
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        */

        for (Enemy tube : enemys) {
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosEnemy().x + tube.getEnemy().getWidth()) {
                tube.reposition(tube.getPosEnemy().x + ((Enemy.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(bird.getHero(), bird.getPosition().x, bird.getPosition().y);
        /*
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }*/
        for (Enemy tube : enemys) {
            sb.draw(tube.getEnemy(), tube.getPosEnemy().x, tube.getPosEnemy().y);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}