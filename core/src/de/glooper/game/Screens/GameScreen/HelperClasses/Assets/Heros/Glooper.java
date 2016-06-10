package de.glooper.game.Screens.GameScreen.HelperClasses.Assets.Heros;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


/**
 * Created by munsel on 31.08.15.
 */
public class Glooper {
    public final AtlasRegion glooper_closed;
    public final AtlasRegion glooper_open;
    public final AtlasRegion glooper_between;



    public Glooper(TextureAtlas atlas) {
        glooper_closed = atlas.findRegion("glooper_closed");
        glooper_between = atlas.findRegion("glooper_between");
        glooper_open = atlas.findRegion("glooper_open");

    }
}
