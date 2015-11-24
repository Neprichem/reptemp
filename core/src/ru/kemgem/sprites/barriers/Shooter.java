package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.kemgem.mainClass;
import ru.kemgem.sprites.EnemyBullet;
import ru.kemgem.sprites.Hero;

/**
 * Created by Danil on 24.11.2015.
 */
public class Shooter extends Enemy {

    public Shooter(float x, float y) {
        super(x, y);

        enemy = new Texture("enemy.png");
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(position.x,position.y, enemy.getWidth(), enemy.getHeight());
    }

    public Vector3 getPosition() { return position; }

    public Texture getTexture() {
        return enemy;
    }

    public void reposition(float x){
        position.set(x, mainClass.HEIGHT/4, 0);
        bounds.setPosition(position.x, position.y);
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
