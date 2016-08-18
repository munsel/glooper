package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.model.Heros.Hero;
import de.glooper.game.Screens.GameScreen.WorldModel;

/**
 * to show the user a indicator of life or stamina
 * somewhere on the Screen.
 * on update, it receives the staminavalue from the hero
 * and creates an according imagemask for the indicator sprites
 * then the renderer can receive the sprites to draw
 *
 * Created by munsel on 02.09.15.
 */
public class StomachStatusDrawer implements Disposable {

    private final float SIZE_X = 1;
    private final float SIZE_y = 1;

    private final int POS_FILLING_X = 1;
    private final int POS_FILLING_Y = 512;
    private final int POS_STOMACH_X = 1;
    private final int POS_STOMACH_Y = 1;

    private final int SIZE_STOMACH_X = 512;
    private final int SIZE_STOMACH_Y = 502;


    private Hero hero;
    private Sprite drawerBackground;
    private Sprite drawerForeground;
    private Texture texture;

    public StomachStatusDrawer(Hero hero){
        this.hero = hero;
        texture = new Texture(Gdx.files.internal("HUD/stomach.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawerBackground = new Sprite(new TextureRegion(texture,
                1,1,512, 502));
        drawerBackground.setSize(SIZE_X,SIZE_y);


        drawerForeground = new Sprite(new TextureRegion(texture,
                1,512,512,502));
        drawerForeground.setSize(SIZE_X, SIZE_y);

    }
    public void update(float camPosX, float camPosY){

        int fillingHeight = (int)(SIZE_STOMACH_Y * hero.getStamina());

        float newSizeY = hero.getStamina()*SIZE_y;

        TextureRegion forgroundTexture;
        forgroundTexture = new TextureRegion(texture,
                POS_FILLING_X,POS_FILLING_Y+(SIZE_STOMACH_Y-fillingHeight),
                SIZE_STOMACH_X,fillingHeight);

        drawerForeground = new Sprite(forgroundTexture);

        //drawerForeground.setTexture(forgroundTexture.getTexture());
        drawerForeground.setSize(SIZE_X,newSizeY);

        drawerBackground.setPosition(camPosX+ WorldModel.VIEWPORT_X/2 - SIZE_X-0.2f,
                camPosY-WorldModel.VIEWPORT_Y/2+0.2f);
        drawerForeground.setPosition(camPosX+ WorldModel.VIEWPORT_X/2 -SIZE_X-0.2f,
                camPosY-WorldModel.VIEWPORT_Y/2+0.2f);
    }

    public void draw(SpriteBatch batch){
        drawerBackground.draw(batch);
        drawerForeground.draw(batch);
    }

    @Override
    public void dispose() {

    }
}
