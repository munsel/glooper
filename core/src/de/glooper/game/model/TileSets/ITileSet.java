package de.glooper.game.model.TileSets;

/**
 * Created by munsel on 15.09.15.
 */
public interface ITileSet {
    String getNameOfSet();
    String getStartTile();
    String[] getAllTextureFileNames();
    String[] getLeftOpenings();
    String[] getRightOpenings();
    String[] getTopOpenings();
    String[] getBottomOpenings();
}
