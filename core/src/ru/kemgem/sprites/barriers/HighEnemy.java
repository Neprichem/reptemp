package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danil on 25.11.2015.
 */
public class HighEnemy {

    Texture texture;
    Vector3 position;
    Shooter sh;
    boolean flive;

    public HighEnemy(Vector3 pos, int type)
    {
        position = pos;
        texture = new Texture("Hide.jpg");
        sh = new Shooter(position.x, position.y + texture.getHeight(), type);
        //sh.addHigh(this);
        flive = true;
    }

    public Texture getTexture(){return texture;}
    public Shooter getShooter(){return sh;}
    public boolean getLive(){return flive;}
    public Vector3 getPosition(){return position;}

    public boolean collides(Rectangle player)
    {
        return player.overlaps(sh.bounds);
    }

    public void death(){flive = false;}

    public void drawHighEnemy(SpriteBatch sb)
    {
        sb.draw(texture, position.x, position.y);
        if (flive)sh.drawShooter(sb);
    }
    public void dispose()
    {
        texture.dispose();
        sh.dispose();
    }
}
