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
    private TextureRegion texture;
    private float maxWidth;

    public SimpleStatusDrawer(Hero hero, Skin skin){
        this.hero = hero;
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        texture = skin.getRegion("default-round");
        maxWidth = texture.getRegionWidth()*10;
        setHeight(15);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setWidth(hero.getStamina()*maxWidth);
    }
}
