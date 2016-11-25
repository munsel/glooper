package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.glooper.game.model.Heros.Hero;

/**
 * Created by munsel on 25.07.16.
 */
public class SimpleStatusDrawer extends Actor{

    private Hero hero;
    private TextureRegion texture, textureBg;
    private float maxWidth;
    private float statusbarWidth;

    public SimpleStatusDrawer(Hero hero, Skin skin){
        this.hero = hero;
        texture = skin.getRegion("life");
        textureBg = skin.getRegion("life_bg");
        maxWidth = 300;
        setWidth(maxWidth);
        setHeight(30);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX()+maxWidth-statusbarWidth, getY(), statusbarWidth, getHeight());
        batch.draw(textureBg, getX(), getY(), getWidth(), getHeight()       );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        statusbarWidth=(hero.getStamina()*maxWidth);
    }
}
