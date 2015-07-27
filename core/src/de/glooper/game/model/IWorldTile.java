package de.glooper.game.model;

/**
 * Created by vincent on 18.07.15.
 */
public interface IWorldTile {
    ITileStrategy getRightStrategy();
    ITileStrategy getLeftStrategy();
    ITileStrategy getUpStrategy();
    ITileStrategy getDownStrategy();
}
