package de.glooper.game.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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



    private float xPos, yPos;
    private float xPosOld, yPosOld;

    private String name, directory;
    private Sprite backgroundSprite;
    private Body body;
    private World world;

    private Array<TileBorderSensor> sensors;
    private OrthographicCamera camera;



    public WorldTile(Texture texture,
                     String bodyFileName, World world,
                     float x, float y, float xOld, float yOld,
                     String directoryName, OrthographicCamera camera){
        directory = directoryName;
        name = bodyFileName;
        backgroundSprite = new Sprite(texture);
        xPos = x;
        yPos = y;
        xPosOld = xOld;
        yPosOld = yOld;
        this.camera = camera;
        //Sprite settings
        backgroundSprite.setPosition(xPos, yPos);
        backgroundSprite.setSize(TILE_SIZE, TILE_SIZE);

        //Body settings
        this.world = world;
       BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal( "WorldTiles/"+directory+"/"+directory+".json"));

        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.f;
        fd.restitution = 0;

        body = world.createBody(bd);


        loader.attachFixture(body, name, fd,TILE_SIZE);

        sensors = new Array<TileBorderSensor>();

    }



    @Override
    public void attachSensor(TileBorderSensor sensor){
        sensors.add(sensor);
    }


    @Override
    public Array<Sprite> getSprites() {
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(backgroundSprite);
        return sprites;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void update(float delta) {
        for( TileBorderSensor sensor: sensors){
            sensor.update(camera.position);
        }
    }
}
