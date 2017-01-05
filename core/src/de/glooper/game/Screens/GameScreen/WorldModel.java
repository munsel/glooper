package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import de.glooper.game.SaveStateManagement.Safeable;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.SaveStateManagement.SaveStateManager;
import de.glooper.game.Screens.GameScreen.HelperClasses.*;
import de.glooper.game.model.Entities.IEntity;
import de.glooper.game.model.Heros.Glooper;
import de.glooper.game.model.Heros.Hero;
import de.glooper.game.model.Background.Clouds;
import de.glooper.game.model.TileWorld.DynamicTileWorld;

/**
 * Created by munsel on 06.06.15.
 */
public class WorldModel implements Safeable, Disposable {
    private static final String TAG = WorldModel.class.getSimpleName();


    public static final float VIEWPORT_X = 8f;
    public static final float VIEWPORT_Y = 4.8f;

    private boolean gameOver;

    private World world;
    private Hero hero;
    private DynamicTileWorld dynamicTileWorld;
    private Clouds clouds;
    private OrthographicCamera camera;
    private CameraHelper cameraHelper;

    private HUD hud;

    private TiledMap map;

    private Array<IEntity> entities;

    private SaveState saveState;

    private GameScreen screen;


    public WorldModel(GameScreen parent){
        this.screen = parent;
        saveState = SaveStateManager.getSaveState();
        gameOver = saveState.isGameOver();

        camera = new OrthographicCamera(VIEWPORT_X, VIEWPORT_Y);

        clouds = new Clouds(camera);
        world = new World(new Vector2(0,0), false);
        map = AssetHandler.instance.map;
        entities = new Array<IEntity>();
        hero = new Glooper(world, camera, this);
        TileObjectUtil.parseTiledObjectLayer(world,entities,map.getLayers().get("collision").getObjects(),hero);
        //dynamicTileWorld = DynamicTileWorld.getInstance(world, hero,camera, saveState);

        hud = new HUD(this.screen, hero);
        cameraHelper = new CameraHelper(this);
        //init();
        //camera.zoom = 15f;
        //camera.update();
    }


    public void update(float delta){
        if (!screen.isPaused() && !gameOver) {
            hero.update(delta);
            world.step(1.f / 60.f, 5, 5);
            //world.step(delta, 5, 5);
//            dynamicTileWorld.update(delta);
            cameraHelper.update(delta);
            for (IEntity entity:entities)entity.update(delta);
            //hud.update(delta, saveState.addToScore(1));//+1 every frame
            clouds.update(delta);
        }
        hud.update(delta);//only score
    }

    public void init(){
        if (gameOver){
            saveState.reset();
            gameOver = false;
           /* Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);
            for (Body body : bodies){
                world.destroyBody(body);
            }*/
            for (IEntity entity: entities){
                //if (entity.isUsed())
                entity.reset();
            }
        }
        //dynamicTileWorld.init();
        hud.restart();
        hero.init(saveState);
        cameraHelper.load(saveState);
        camera.update();
        clouds.init();
    }



    public void addToScore(int inc){saveState.addToScore(inc);}

    public OrthographicCamera getCamera(){return camera;}

/*    public void drawBackground(SpriteBatch batch){
        dynamicTileWorld.draw(batch);
    }*/

    public Hero getHero(){
        return hero;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        this.gameOver = true;
        saveState.setGameOver(true);
        hud.gameOver(saveState.getScore());
    }


    public SaveState getSaveState(){
        return saveState;
    }
    /*public String getTileName(){
        return dynamicTileWorld.getCurrentName();
    }*/

    public World getWorld() {
        return world;
    }

    public HUD getHud() {
        return hud;
    }

    @Override
    public void dispose() {

    }

    public Array<IEntity> getEntities(){return entities;}

    public TiledMap getMap(){
        return map;
    }
    public void debugDrawSensors(ShapeRenderer shapeRenderer){
        //dynamicTileWorld.drawDebugSensors(shapeRenderer);
    }

    public Clouds getClouds() {
        return clouds;
    }

    @Override
    public void save(SaveState saveState) {
        hero.save(saveState);
        saveState.setGameOver(isGameOver());
       // dynamicTileWorld.save(saveState);
        cameraHelper.save(saveState);
    }

    @Override
    public void load(SaveState saveState) {
        //not used here! the init() method handles this
    }
}
