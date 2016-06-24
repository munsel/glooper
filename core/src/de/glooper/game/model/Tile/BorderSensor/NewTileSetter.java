package de.glooper.game.model.Tile.BorderSensor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import de.glooper.game.model.IDynamicWorld;
import de.glooper.game.model.Tile.WorldTile;

/**
 * Created by munsel on 11.06.16.
 */
public class NewTileSetter implements  ITileBorderSensorBehaviour {
    private static final String TAG = NewTileSetter.class.getSimpleName();

    private IDynamicWorld world;

    public NewTileSetter(IDynamicWorld world){
        this.world = world;
    }

    @Override
    public void update(float nextX, float nextY, WorldTile.DIRECTION direction) {
        world.createNewTile(nextX, nextY, direction);
        Gdx.app.log(TAG, "created new motherfucker!");
    }
}
