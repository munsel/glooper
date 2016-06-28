package de.glooper.game.model.TileSets;

/**
 * Created by munsel on 15.09.15.
 */
public class StarterTiles implements ITileSet {




    private final String setName = "starters";

    private final String startTile = "LRBU";

    private final String[] allTileTextureNames = {"BL", "BUL",
            "LR", "LRBU", "LU", "BU","B"};

    private final String[] leftOpenings = {"BL", "BUL",
                                "LR", "LRBU", "LU"};
    private final String[] rightOpenings = {"LR", "LRBU"};
    private final String[] topOpenings = {"BU","BUL","LRBU","LU"};
    private final String[] bottomOpenings = {"LRBU", "BU", "BUL","B"};


    @Override
    public String getNameOfSet() {
        return setName;
    }

    @Override
    public String getStartTile() {
        return startTile;
    }

    @Override
    public String[] getAllTextureFileNames() {
        return allTileTextureNames;
    }

    @Override
    public String[] getLeftOpenings() {
        return leftOpenings;
    }

    @Override
    public String[] getRightOpenings() {
        return rightOpenings;
    }

    @Override
    public String[] getTopOpenings() {
        return topOpenings;
    }

    @Override
    public String[] getBottomOpenings() {
        return bottomOpenings;
    }
}
