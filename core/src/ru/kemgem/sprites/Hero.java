package ru.kemgem.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.mainClass;

//import javafx.animation.Animation;

//import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Vitaly on 06.11.2015.
 */
/**
 * redacted by Danil on 21.11.2015.
 */
public class Hero {
    private static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;

    private static final int FRAME_COLS = 6;//справйты по вертикали
    private static final int FRAME_ROWS = 5;//по горизонтали

    Animation heroAnimation;//анимация
    private Texture hero;//содержит все спрайты в виде одного изображения

    TextureRegion[] heroFrames;//массив всех спрайтов
    SpriteBatch spriteBatch;//риусет текстуру на экране
    TextureRegion currentFrame;//текущий кадр

    float stateTime;//время с начала анимации

    public Hero(int x, int y){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        hero = new Texture(Gdx.files.internal("sprite-animation4.png"));//создание текстуры
        TextureRegion[][] tmp = TextureRegion.split(hero, hero.getWidth()/FRAME_COLS, hero.getHeight()/FRAME_ROWS);
        heroFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                heroFrames[index++] = tmp[i][j];
            }
        }//создание двумерного массива спрайтов
         // это типа должен быть конструктор аниматион
        heroAnimation = new Animation(0.025f, heroFrames);
       // spriteBatch = new SpriteBatch(); // инициализация для рисования кадра
        stateTime = 0f; // обновление времени
        bounds = new Rectangle(x, y, hero.getWidth()/FRAME_COLS, hero.getHeight()/FRAME_ROWS);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getHeroRegion() {
        return currentFrame;
    }

    //обработчик движения
    public void update(float dt){
        if (position.y > 0)
            velosity.add(0, GRAVITY, 0);//действие гравитации
        velosity.scl(dt);
        position.add(MOVEMENT * dt, velosity.y, 0);//изменение координат движения
        if (position.y < mainClass.HEIGHT/4)
            position.y = mainClass.HEIGHT/4;
        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
        //stateTime += Gdx.graphics.getDeltaTime(); // #15
        ///currentFrame = heroAnimation.getKeyFrame(stateTime, true); // #16
    }

    //Прыжок
    public void jump(){
        velosity.y = 250;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    //мутод отрисовки героя
    public void drawHero(SpriteBatch sb) {
        stateTime += Gdx.graphics.getDeltaTime(); // #15
        currentFrame = heroAnimation.getKeyFrame(stateTime, true); // #16
        sb.draw(currentFrame, position.x, position.y); // #17
    }
}