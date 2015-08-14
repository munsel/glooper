package de.glooper.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTile implements IWorldTile, Disposable{
    private static final String TAG = WorldTile.class.getSimpleName();

    public final static float TILE_SIZE = 16f;


    private ITileStrategy leftStrategy;
    private ITileStrategy rightStrategy;
    private ITileStrategy upStrategy;
    private ITileStrategy downStrategy;

    private float xPos, yPos;
    private float xPosOld, yPosOld;

    private Sprite backgroundSprite;

    public WorldTile(Texture texture, float x, float y, float xOld, float yOld){
        backgroundSprite = new Sprite(texture);
        xPos = x;
        yPos = y;
        xPosOld = xOld;
        yPosOld = yOld;
        backgroundSprite.setPosition(xPos, yPos);
        backgroundSprite.setSize(TILE_SIZE, TILE_SIZE);


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
        this.backgroundSprite = sprite;
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

    @Override
    public Array<Sprite> getSprites() {
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(backgroundSprite);
        return sprites;
    }



    @Override
    public void dispose() {
    }
}
