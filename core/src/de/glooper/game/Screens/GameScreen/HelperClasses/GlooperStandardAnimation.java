package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by munsel on 03.08.15.
 */
public class GlooperStandardAnimation implements IHeroAsset {
    /**
     * this holds the filename of the textureAtlas for the
     * standard animation of the hero
     */
    private final String FILENAME="";
    private Array<TextureRegion> frames;

    @Override
    public Array<? extends TextureRegion> getStandardAnimation() {
        return frames;
    }

    @Override
    public void setStandardAnimation(Array<? extends TextureRegion> newAnimation) {
        frames = (Array<TextureRegion>) newAnimation;
    }
}
