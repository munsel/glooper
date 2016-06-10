package de.glooper.game.model.TileSets;

/**
 * Created by munsel on 15.09.15.
 */
public class StarterTiles implements ITileSet {

    private final String setName = "starters";

    private final String startTile = "xCross";

    private final String[] leftOpenings = {"xCross"};
    private final String[] rightOpenings = {"xCross"};
    private final String[] topOpenings = {"xCross"};
    private final String[] bottomOpenings = {"xCross"};


    @Override
    public String getNameOfSet() {
        return setName;
    }

    @Override
    public String getStartTile() {
        return startTile;
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
