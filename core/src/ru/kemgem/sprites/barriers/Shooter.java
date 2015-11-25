package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import ru.kemgem.mainClass;
import ru.kemgem.sprites.EnemyBullet;
import ru.kemgem.sprites.Hero;

/**
 * Created by Danil on 24.11.2015.
 */
public class Shooter extends Enemy {

    private static final int FRAME_COLS = 10; // #1
    private static final int FRAME_ROWS = 1; // #2

    Animation shooterAnimation;
    TextureRegion[] shooterFrames;
    TextureRegion currentFrame;
    SpriteBatch spriteBatch;
    float stateTime;
    public Shooter(float x, float y) {
        super(x, y);

        enemy = new Texture("shooter.png");
        position = new Vector3(x, y, 0);

        bounds = new Rectangle(position.x,position.y, enemy.getWidth(), enemy.getHeight());
        TextureRegion[][] tmp = TextureRegion.split(enemy, enemy.getWidth()/FRAME_COLS, enemy.getHeight()/FRAME_ROWS); // #10
        shooterFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
               shooterFrames[index++] = tmp[i][j];
            }
        }
        shooterAnimation = new Animation(0.025f, shooterFrames); // #1
        spriteBatch = new SpriteBatch();
        stateTime = 0f; // #13
    }

    public Vector3 getPosition() { return position; }

    public Texture getTexture() {
        return enemy;
    }

    public void reposition(float x){
        position.set(x, mainClass.HEIGHT/4, 0);
        bounds.setPosition(position.x, position.y);

    }

    public void render()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // #15
        currentFrame = shooterAnimation.getKeyFrame(stateTime, true); // #16
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, position.x, position.y); // #17
        spriteBatch.end();
    }

    public void shot(Array<EnemyBullet> bullets, Hero hero)
    {
        bullets.add(new EnemyBullet(position, hero.getPosition().x, hero.getPosition().y));
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public void dispose()
    {
        enemy.dispose();
    }
}
