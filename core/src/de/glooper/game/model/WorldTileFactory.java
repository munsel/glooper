package de.glooper.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTileFactory {

    private Array<IWorldTile> tiles = new Array<IWorldTile>();
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
    public IWorldTile getStartTile(){


        return new WorldTile(AssetHandler.instance.firstWorldTileAsset.getTexture(),
                0,0,0,0);
    }

    public IWorldTile getWorldTile(IWorldTile neighbour){

        int i = 0;
        //while(comp.compare(tiles.get(i).getRightStrategy(), neighbour.getLeftStrategy())) {
        //    i++;
       // }


        return null;
    }

    //public void setComparator(Comparator<WorldTile> comp) {
      //  this.comp = comp;
    //}

}
