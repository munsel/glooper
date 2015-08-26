package de.glooper.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.model.IDynamicWorld;

/**
 * Created by munsel on 12.08.15.
 */
public class DynamicTileWorld implements IDynamicWorld {
    private static final String TAG = DynamicTileWorld.class.getSimpleName();

    private Array<IWorldTile> tiles;
    public static DynamicTileWorld instance;

    private WorldTileFactory facory;
    private World world;

    private DynamicTileWorld(World world){
        init(world);
    }

    @Override
    public void init(World world) {
        this.world = world;
        tiles = new Array<IWorldTile>();
        facory = WorldTileFactory.getInstance();
        tiles.add(facory.getStartTile(world));


    }

    @Override
    public void createNewTile(float x, float y, WorldTile.DIRECTION direction) {
        tiles.add( facory.getWorldTile(x,y,direction) );

    }


    public static IDynamicWorld getInstance(World world) {
        if (instance == null)
            instance = new DynamicTileWorld(world);

        return instance;
    }



    @Override
    public Array<Sprite> getSprites() {
        Array<Sprite> sprites = new Array<Sprite>();
        for (IWorldTile tile : tiles){
            for(Sprite sprite: tile.getSprites() ){
                sprites.add(sprite);
            }
        }
        return sprites;
    }
}
