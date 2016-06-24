package de.glooper.game.model.Tile;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.Heros.Hero;
import de.glooper.game.model.DynamicTileWorld;
import de.glooper.game.model.IDynamicWorld;
import de.glooper.game.model.Tile.BorderSensor.NewTileSetter;
import de.glooper.game.model.Tile.BorderSensor.TileBorderSensor;
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

    private Vector2 heroPos;

    private float xPosOld, yPosOld;

    private String name, directory;
    private Sprite backgroundSprite;
    private Body body;

    private World world;
    private IDynamicWorld dynamicWorld;
    private Hero hero;

    private float timeInside;

    private Array<TileBorderSensor> sensors;
    private OrthographicCamera camera;



    public WorldTile(Texture texture,
                     String bodyFileName,
                     World world, IDynamicWorld dynamicWorld,
                     Hero hero,
                     float x, float y, float xOld, float yOld,
                     String directoryName, OrthographicCamera camera)
    {
        directory = directoryName;
        name = bodyFileName;
        backgroundSprite = new Sprite(texture);
        timeInside = 0;
        xPos = x;
        yPos = y;
        xPosOld = xOld;
        yPosOld = yOld;
        this.camera = camera;
        this.dynamicWorld = dynamicWorld;
        this.hero = hero;
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
    public boolean isHeroInside() {
        heroPos = hero.getPosition();
        if (heroPos.x>xPos && heroPos.x<xPos+WorldTile.TILE_SIZE){
            if (heroPos.y>yPos && heroPos.y<yPos+WorldTile.TILE_SIZE){
                return true;
            }
        }
        return false;
    }

    @Override
    public void drawDrebugSensors(ShapeRenderer shapeRenderer) {
        for(TileBorderSensor sensor: sensors){
            sensor.drawDebugSensors(shapeRenderer);
        }
    }

    @Override
    public void removeBody() {
        world.destroyBody(body);
    }


    @Override
    public void draw(SpriteBatch batch) {
        backgroundSprite.draw(batch);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameOfSet() {
        return directory;
    }

    @Override
    public float getX() {
        return backgroundSprite.getX();
    }

    @Override
    public float getY() {
        return backgroundSprite.getY();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void update(float delta) {
        if (isHeroInside()) {
            //Gdx.app.log(TAG, "is inside: x:"+Float.toString(xPos)+" y:"+ Float.toString(yPos));
            timeInside += delta;
            if (dynamicWorld.getCurrentTile() != this) {
                //Gdx.app.log(TAG, "this tile is not current one!");
                if (timeInside > 3) {
                    //Gdx.app.log(TAG, "now set this to current!");
                    dynamicWorld.setCurrentTile(this);
                    sensors.clear();
                    sensors.add(new TileBorderSensor(xPos,yPos,DIRECTION.DOWN, this, hero, new NewTileSetter(dynamicWorld)));
                    sensors.add(new TileBorderSensor(xPos,yPos,DIRECTION.UP, this, hero, new NewTileSetter(dynamicWorld)));
                    sensors.add(new TileBorderSensor(xPos,yPos,DIRECTION.LEFT, this, hero, new NewTileSetter(dynamicWorld)));
                    sensors.add(new TileBorderSensor(xPos,yPos,DIRECTION.RIGHT, this, hero, new NewTileSetter(dynamicWorld)));
                }
            }
            if (dynamicWorld.getCurrentTile() == this) {
                for (TileBorderSensor sensor : sensors) {
                    sensor.update(camera.position);
                }
            }
        }else{
            timeInside = 0;
        }

    }
}
