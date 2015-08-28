package de.glooper.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTileFactory {

    private ITileStrategyComparator comp;
    private static WorldTileFactory instance;




    private WorldTileFactory() {
        AssetHandler.instance.init(new AssetManager());

    }
    public static WorldTileFactory getInstance() {
        if (instance == null) {

                    instance = new WorldTileFactory();

            }

        return instance;
    }

    /**
     * this gets called when the game will be initialized
     * @return the start tile, which must have been loaded by
     * the assethandler firstly
     */
    public IWorldTile getStartTile(World world){


        return new WorldTile(AssetHandler.instance.firstWorldTileAsset.getTexture(),
                "xCross",world,0,0,0,0,"starters");
    }

    /**
     *
     * @param x and
     * @param y are the coordinates of the new tile
     * @param direction is the side, that must be open
     *                  according to the direction, this method
     *                  should create a new WorldTile, that fits
     * @return
     */
    public IWorldTile getWorldTile(float x, float y, WorldTile.DIRECTION direction){


        /*

        IWorldTile newTile = new WorldTile(AssetHandler.instance.secondWolrdTileAsset.getTexture()
                ,x,y,
                )
        */

        return null;
    }

    //public void setComparator(Comparator<WorldTile> comp) {
      //  this.comp = comp;
    //}

}
