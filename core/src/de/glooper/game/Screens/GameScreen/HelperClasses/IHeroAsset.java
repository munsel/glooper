package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by munsel on 03.08.15.
 */
public interface IHeroAsset {
    Array<? extends TextureRegion> getStandardAnimation();
    void setStandardAnimation(Array<? extends TextureRegion> newAnimation);

}
