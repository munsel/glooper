package de.glooper.game.model;

/**
 * Created by vincent on 18.07.15.
 */
public class DynamicNineTileWorld implements IDynamicWorld{

    private IWorldTile[] worldtiles;

    private static  DynamicNineTileWorld instance;

    private DynamicNineTileWorld() {
        init();

    }
    public static DynamicNineTileWorld getInstance() {
        if (instance == null)
                instance = new DynamicNineTileWorld();

        return instance;
    }

    void init() {
    }


    public void createWorld(int position) {
        IWorldTile temp = worldtiles[position];
        if(temp.equals(worldtiles[1])) {
            worldtiles[6] = worldtiles[3];
            worldtiles[7] = worldtiles[4];
            worldtiles[8] = worldtiles[5];
            worldtiles[3] = worldtiles[0];
            worldtiles[4] = worldtiles[1];
            worldtiles[5] = worldtiles[2];
            /*
            * Code fürs freigeben der worldtile 012
            *
            * */
            //worldtiles[0] = WorldTileFactory.getWorldTile(worldtiles[3]);
           // worldtiles[1] = WorldTileFactory.getWorldTile(worldtiles[4]);
          //  worldtiles[2] = WorldTileFactory.getWorldTile(worldtiles[5]);
        }
        else if(temp.equals(worldtiles[1])) {
                worldtiles[6] = worldtiles[3];
                worldtiles[7] = worldtiles[4];
                worldtiles[8] = worldtiles[5];
                worldtiles[3] = worldtiles[0];
                worldtiles[4] = worldtiles[1];
                worldtiles[5] = worldtiles[2];
            /*
            * Code fürs freigeben der worldtile 012
            *
            * */
              //  worldtiles[0] = WorldTileFactory.getWorldTile();
              //  worldtiles[1] = WorldTileFactory.getWorldTile();
             //   worldtiles[2] = WorldTileFactory.getWorldTile();
        }


    }


    public IWorldTile getWorldtiele11() {
        return worldtiles[0];
    }

    public void setWorldtiele11(IWorldTile worldtiele11) {
        this.worldtiles[0] = worldtiele11;
    }

    public IWorldTile getWorldtiele21() {
        return worldtiles[1];
    }

    public void setWorldtiele21(IWorldTile worldtiele21) {
        this.worldtiles[1] = worldtiele21;
    }

    public IWorldTile getWorldtiele31() {
        return worldtiles[2];
    }

    public void setWorldtiele31(IWorldTile worldtiele31) {
        this.worldtiles[2] = worldtiele31;
    }

    public IWorldTile getWorldtiele12() {
        return worldtiles[3];
    }

    public void setWorldtiele12(IWorldTile worldtiele12) {
        this.worldtiles[3] = worldtiele12;
    }

    public IWorldTile getWorldtiele22() {
        return worldtiles[4];
    }

    public void setWorldtiele22(IWorldTile worldtiele22) {
        this.worldtiles[4] = worldtiele22;
    }

    public IWorldTile getWorldtiele32() {
        return worldtiles[5];
    }

    public void setWorldtiele32(IWorldTile worldtiele32) {
        this.worldtiles[5] = worldtiele32;
    }

    public IWorldTile getWorldtiele13() {
        return worldtiles[6];
    }

    public void setWorldtiele13(IWorldTile worldtiele13) {
        this.worldtiles[6] = worldtiele13;
    }

    public IWorldTile getWorldtiele23() {
        return worldtiles[7];
    }

    public void setWorldtiele23(IWorldTile worldtiele23) {
        this.worldtiles[7] = worldtiele23;
    }

    public IWorldTile getWorldtiele33() {
        return worldtiles[8];
    }

    public void setWorldtiele33(IWorldTile worldtiele33) {
        this.worldtiles[8] = worldtiele33;
    }



}
