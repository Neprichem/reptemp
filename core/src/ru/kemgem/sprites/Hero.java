package ru.kemgem.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ru.kemgem.MainClass;
import ru.kemgem.states.GameOver;

/**
 * redacted by Danil on 21.11.2015.
 */
public class Hero {
    //константы
    private static final int MOVEMENT = 150;//скорость
    private static final int GRAVITY = -15;//гравитация
    private static final int FRAME_COLS = 6;//справйты по вертикали
    private static final int FRAME_ROWS = 5;//по горизонтали

    private Vector3 position;//вектор координат
    private Vector3 velosity;//вектор изменения позиции
    private Rectangle bounds;//кубическая проекция

    private Animation heroAnimation;//анимация
    private Texture hero;//содержит все спрайты в виде одного изображения

    int countLive;
    Texture liveTexture;

    private TextureRegion[] heroFrames;//массив всех спрайтов
    TextureRegion currentFrame;//текущий кадр

    float stateTime;//время с начала анимации

    public Hero(int x, int y){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);

        hero = new Texture(Gdx.files.internal("sprite-animation4.png"));//создание текстуры

        countLive = 5;
        liveTexture = new Texture("live.png");

        TextureRegion[][] tmp = TextureRegion.split(hero, hero.getWidth()/FRAME_COLS, hero.getHeight()/FRAME_ROWS);

        heroFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;

        //создание двумерного массива спрайтов
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                heroFrames[index++] = tmp[i][j];
            }
        }

         // это типа должен быть конструктор аниматион
        heroAnimation = new Animation(0.025f, heroFrames);
        stateTime = 0f; // обновление времени
        bounds = new Rectangle(x, y, hero.getWidth()/FRAME_COLS, hero.getHeight()/FRAME_ROWS);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getHero() {
        return hero;
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
        if (position.y < MainClass.HEIGHT/4)//граница пола, чтобы не проваливался
            position.y = MainClass.HEIGHT/4;
        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
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

    public void drawHeroLive(SpriteBatch sb, float x) {
        if (countLive <= 3) {
            for (int i = 1; i <= countLive; i++) {
                sb.draw(liveTexture, x + i * 27, MainClass.HEIGHT - 24*2,
                        24, 24); // #17
            }
        }
        else {
            BitmapFont font = new BitmapFont();
            font.draw(sb, "Live: " +countLive ,  x + 20, MainClass.HEIGHT - 24);
        }
    }

    public void death()
    {
        countLive--;
        if (countLive <= 0) {
            MainClass.gsm.push(new GameOver(MainClass.gsm));
        }
    }


    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }
}