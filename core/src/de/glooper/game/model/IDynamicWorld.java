package de.glooper.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vincent on 18.07.15.
 */
public interface IDynamicWorld {
    void init();
    IWorldTile getNewTile();
    Array<Sprite> getSprites();
}
