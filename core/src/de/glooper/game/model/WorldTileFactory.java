package de.glooper.game.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTileFactory {

    private List<IWorldTile> tiles = new ArrayList<IWorldTile>();
    private ITileStrategyComparator comp;
    private static volatile WorldTileFactory instance;

    private WorldTileFactory() {

    }
    public static WorldTileFactory getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new WorldTileFactory();
                }
            }
        }
        return instance;
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
