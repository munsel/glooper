package de.glooper.game.model;

/**
 * Created by vincent on 18.07.15.
 */
public class TileStrategy implements ITileStrategy {

    @Override
    public boolean isEntrance() {
        return false;
    }

    enum Condition{WATER, ROCK}
    Condition condition;
    
    void setCondition(Condition c) {
        this.condition = c;
    }


}
