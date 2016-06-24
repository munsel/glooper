package de.glooper.game.model.Tile.BorderSensor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import de.glooper.game.model.IDynamicWorld;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.WorldTile;

/**
 * Created by munsel on 10.06.16.
 */
public class CurrentTileSetter implements ITileBorderSensorBehaviour {
    private static final String TAG = CurrentTileSetter.class.getSimpleName();

    private IDynamicWorld world;
    private IWorldTile parent;

    public CurrentTileSetter(IDynamicWorld world, IWorldTile parentTile){
        this.world = world;
        this.parent = parentTile;

    }

    @Override
    public void update(float nextX, float nextY, WorldTile.DIRECTION direction) {
        world.setCurrentTile(parent);
        Gdx.app.log(TAG, "setting this little bastard as current tile!");
    }
}
