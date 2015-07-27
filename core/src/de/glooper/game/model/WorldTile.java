package de.glooper.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTile implements IWorldTile {

    private ITileStrategy leftStrategy;
    private ITileStrategy rightStrategy;
    private ITileStrategy upStrategy;
    private ITileStrategy downStrategy;

    private Sprite sprite;

    public WorldTile(){


    }


    public void setLeftStrategy(ITileStrategy leftStrategy) {
        this.leftStrategy = leftStrategy;
    }

    public void setRightStrategy(ITileStrategy rightStrategy) {
        this.rightStrategy = rightStrategy;
    }

    public void setUpStrategy(ITileStrategy upStrategy) {
        this.upStrategy = upStrategy;
    }

    public void setDownStrategy(ITileStrategy downStrategy) {
        this.downStrategy = downStrategy;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public ITileStrategy getLeftStrategy() {
        return leftStrategy;
    }

    public ITileStrategy getRightStrategy() {
        return rightStrategy;
    }

    public ITileStrategy getUpStrategy() {
        return upStrategy;
    }

    public ITileStrategy getDownStrategy() {
        return downStrategy;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
