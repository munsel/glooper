package de.glooper.game.model.Tile.BorderSensor;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.WorldTile;

/**
 * Created by munsel on 25.08.15.
 */
public interface ITileBorderSensorBehaviour {
    void update( float nextX, float nextY, WorldTile.DIRECTION direction);
}
