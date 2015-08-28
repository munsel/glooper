package de.glooper.game.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import libs.BodyEditorLoader;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTile implements IWorldTile, Disposable{
    private static final String TAG = WorldTile.class.getSimpleName();

    public final static float TILE_SIZE = 16f;

    /**
     * this is needed for the checks and to mark, which
     * side must be open
     */
    public enum DIRECTION{UP, DOWN, LEFT, RIGHT}

    /**
     * this is to determine a type of difficulty
     * for future use
     */
    public enum TYPE{START, ONE_MIN, THREE_MIN, FIVE_MIN}

    private ITileStrategy leftStrategy;
    private ITileStrategy rightStrategy;
    private ITileStrategy upStrategy;
    private ITileStrategy downStrategy;

    private float xPos, yPos;
    private float xPosOld, yPosOld;

    private Sprite backgroundSprite;
    private Body body;
    private World world;

    public WorldTile(Texture texture,
                     String bodyFileName, World world,
                     float x, float y, float xOld, float yOld,
                     String directoryName){
        backgroundSprite = new Sprite(texture);
        xPos = x;
        yPos = y;
        xPosOld = xOld;
        yPosOld = yOld;
        //Sprite settings
        backgroundSprite.setPosition(xPos, yPos);
        backgroundSprite.setSize(TILE_SIZE, TILE_SIZE);

        //Body settings
        this.world = world;
       BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal( "WorldTiles/"+directoryName+"/"+directoryName+".json"));

        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.f;
        fd.restitution = 0;

        body = world.createBody(bd);


        loader.attachFixture(body, /*"WorldTiles/" + directoryName + "/"+*/bodyFileName, fd,TILE_SIZE);








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
