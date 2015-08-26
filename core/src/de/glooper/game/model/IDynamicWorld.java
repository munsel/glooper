package de.glooper.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vincent on 18.07.15.
 */
public interface IDynamicWorld {
    void init(World world);
    void createNewTile( float x, float y, WorldTile.DIRECTION direction);
    Array<Sprite> getSprites();
}
