package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.SaveStateManagement.SaveStateManager;
import de.glooper.game.Screens.GameScreen.HelperClasses.CameraHelper;
import de.glooper.game.Screens.GameScreen.HelperClasses.HUD;
import de.glooper.game.Screens.GameScreen.HelperClasses.HeroStatusDrawer;
import de.glooper.game.Screens.GameScreen.Heros.Glooper;
import de.glooper.game.Screens.GameScreen.Heros.Hero;
import de.glooper.game.model.DynamicTileWorld;
import de.glooper.game.model.IDynamicWorld;
import de.glooper.game.model.WorldTile;

/**
 * Created by munsel on 06.06.15.
 */
public class WorldModel implements Disposable {
    private static final String TAG = WorldModel.class.getSimpleName();


    public static final float VIEWPORT_X = 8f;
    public static final float VIEWPORT_Y = 4.8f;

    private boolean gameOver;

    private World world;
    private Hero hero;
    private IDynamicWorld dynamicTileWorld;
    private OrthographicCamera camera;
    private CameraHelper cameraHelper;

    private HeroStatusDrawer statusDrawer;
    private HUD hud;

    private Pool enemies;

    private SaveState saveState;

    private GameScreen screen;


    public WorldModel(GameScreen parent){
        this.screen = parent;
        saveState = SaveStateManager.getSaveState();

        camera = new OrthographicCamera(VIEWPORT_X, VIEWPORT_Y);

        init();

        world = new World(new Vector2(0,0), false);
        dynamicTileWorld = DynamicTileWorld.getInstance(world, camera);
        hero = new Glooper(world, camera);

        statusDrawer = new HeroStatusDrawer(hero);
        hud = new HUD(this.screen);
        cameraHelper = new CameraHelper(this);
        //camera.zoom = 3f;
        //camera.update();
    }


    public void update(float delta){
        if (!screen.isPaused()) {
            hero.update(delta);
            world.step(1.f / 60.f, 5, 5);
            dynamicTileWorld.update(delta);
            cameraHelper.update(delta);
            statusDrawer.update(camera.position.x, camera.position.y);
            hud.update(delta, saveState.addToScore(1));
        }
    }

    public void init(){
        camera.position.set(WorldTile.TILE_SIZE/2, WorldTile.TILE_SIZE/2,0);
    }

    public void gameOver(){
        saveState.reset();
        SaveStateManager.saveSaveState(saveState);
    }

    public OrthographicCamera getCamera(){return camera;}

    public void drawBackground(SpriteBatch batch){
        dynamicTileWorld.draw(batch);
    }

    public Hero getHero(){
        return hero;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Vector2 getTilePos(){
        return dynamicTileWorld.getCurrentPos();
    }

    public String getTileName(){
        return dynamicTileWorld.getCurrentName();
    }

    public World getWorld() {
        return world;
    }

    public HUD getHud() {
        return hud;
    }
    public HeroStatusDrawer getStatusDrawer() {
        return statusDrawer;
    }

    @Override
    public void dispose() {

    }



    public void debugDrawSensors(ShapeRenderer shapeRenderer){
        dynamicTileWorld.drawDebugSensors(shapeRenderer);
    }
}
