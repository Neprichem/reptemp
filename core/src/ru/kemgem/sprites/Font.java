package ru.kemgem.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Danil on 26.11.2015.
 */
/*
Реализация класса фона
фон работает путем повторения 2 изображений, поочередно сменяющих другг друга
 */
public class Font {
    private Vector3 position1, position2;//координаты текстур
    private Texture bg1, bg2;//2 текстуры

    public Font()
    {
        bg1 = new Texture("bg42.jpg");
        bg2 = new Texture("bg43.jpg");
        position1 = new Vector3(0,0,0);
        position2 = new Vector3(bg1.getWidth(),0,0);//по Х сразу за 1 текстурой
    }


    public void drawFont(SpriteBatch sb, float x){
        //отрисовка
        sb.draw(bg1, position1.x, 0);
        sb.draw(bg2, position2.x, 0);
        //обновление координат
        if (position2.x < x)
            position1.x = position2.x + bg2.getWidth();
        if (position1.x < x)
            position2.x = position1.x + bg1.getWidth();
    }

    public void dispose()
    {
        bg1.dispose();
        bg2.dispose();
    }
}
