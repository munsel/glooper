package de.glooper.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.model.IDynamicWorld;

/**
 * Created by munsel on 12.08.15.
 */
public class DynamicTileWorld implements IDynamicWorld {
    private static final String TAG = DynamicTileWorld.class.getSimpleName();

    private Array<IWorldTile> tiles;
    public static DynamicTileWorld instance = new DynamicTileWorld();

    private WorldTileFactory facory;

    private DynamicTileWorld(){
        init();
    }

    @Override
    public void init() {
        tiles = new Array<IWorldTile>();
        facory = WorldTileFactory.getInstance();
        tiles.add(facory.getStartTile());


    }



    public static IDynamicWorld getInstance() {
        if (instance == null)
            instance = new DynamicTileWorld();

        return instance;
    }

    @Override
    public IWorldTile getNewTile() {
        return null;
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
