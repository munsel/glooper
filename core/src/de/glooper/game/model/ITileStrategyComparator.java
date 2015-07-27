package de.glooper.game.model;

import java.util.Comparator;

/**
 * Created by vincent on 18.07.15.
 */
public interface ITileStrategyComparator extends Comparator<TileStrategy> {
    int compare(TileStrategy a, TileStrategy b);
}
