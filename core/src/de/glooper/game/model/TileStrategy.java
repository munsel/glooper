package de.glooper.game.model;

/**
 * Created by vincent on 18.07.15.
 */
public class TileStrategy implements ITileStrategy {

    enum Condition{WATER, ROCK}
    Condition condition;
    
    void setCondition(Condition c) {
        this.condition = c;
    }
}
