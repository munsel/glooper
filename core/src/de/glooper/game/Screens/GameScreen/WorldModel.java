package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.GlooperMainClass;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.SaveStateManagement.SaveStateManager;
import de.glooper.game.SaveStateManagement.SettingsState;
import de.glooper.game.SaveStateManagement.SettingsManager;
import de.glooper.game.Screens.GameScreen.HelperClasses.CameraHelper;
import de.glooper.game.Screens.GameScreen.HelperClasses.HUD;
import de.glooper.game.Screens.GameScreen.HelperClasses.HeroStatusDrawer;
import de.glooper.game.Screens.GameScreen.Heros.Glooper;
import de.glooper.game.Screens.GameScreen.Heros.Hero;
import de.glooper.game.Screens.GameScreen.Heros.IHero;
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

    private SettingsManager settingsManager;
    private SettingsState settingsState;

    private SaveState saveState;


    public WorldModel(GlooperMainClass mainParent){
        settingsManager = new SettingsManager(this);

        saveState = SaveStateManager.getSaveState();

        camera = new OrthographicCamera(VIEWPORT_X, VIEWPORT_Y);
        init();

        //camera.position.x = WorldTile.TILE_SIZE/2;
        //camera.position.y = 0.5f*WorldTile.TILE_SIZE/2;


        world = new World(new Vector2(0,0), false);
        dynamicTileWorld = DynamicTileWorld.getInstance(world, camera);
        hero = new Glooper(world, camera);
        statusDrawer = new HeroStatusDrawer(hero);
        hud = new HUD();

        cameraHelper = new CameraHelper(this);


       // camera.zoom = 2f;
        //camera.update();
    }


    public void update(float delta){
        hero.update(delta);
        world.step(1.f / 60.f, 5, 5);
        dynamicTileWorld.update(delta);
        cameraHelper.update(delta);
        statusDrawer.update(camera.position.x, camera.position.y);
        hud.update(delta, saveState.addToScore(1));
    }

    public void init(){
        camera.position.set(WorldTile.TILE_SIZE/2, WorldTile.TILE_SIZE/2,0);


    }

    public void gameOver(){

        saveState.reset();
        SaveStateManager.SaveSaveState(saveState);
    }






    public OrthographicCamera getCamera(){return camera;}


    public Array<Sprite> getBackgroundSpritesToDraw(){
        Array<Sprite> sprites = new Array<Sprite>();


        for (Sprite sprite : dynamicTileWorld.getSprites()) {
            sprites.add(sprite);
        }

        return sprites;
    }

    public Stage getHud(){return hud;}
    public Array<Sprite> getHUDSpritesToDraw(){
        return statusDrawer.getSprites();
    }
    public IHero getHero(){
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

    @Override
    public void dispose() {

    }
}
