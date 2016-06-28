package de.glooper.game.model.TileWorld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.WorldTile;

/**
 * Created by vincent on 18.07.15.
 */
public interface IDynamicWorld {
    void init();
    void createNewTile( float x, float y, WorldTile.DIRECTION direction);
    void setCurrentTile(IWorldTile newCurrentTile);
    IWorldTile getCurrentTile();
    String getCurrentName();
    void update(float delta);
    void draw(SpriteBatch batch);
    void drawDebugSensors(ShapeRenderer shapeRenderer);
}
