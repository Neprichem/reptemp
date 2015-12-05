package ru.kemgem.sprites.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danil on 01.12.2015.
 */
public class StateBarriers {
    private Texture texture;
    private Vector3 position;
    private int type;
    private Rectangle bounds;

    public StateBarriers(Vector3 pos, int _type)
    {
        texture = new Texture("tank.jpg");
        position = new Vector3(pos);
        type = _type;
        bounds = new Rectangle(position.x,position.y, texture.getWidth(), texture.getHeight());
    }

    public Vector3 getPosition(){return position;}
    public Texture getTexture(){return texture;}

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public void dispose()
    {
        texture.dispose();
    }
}
