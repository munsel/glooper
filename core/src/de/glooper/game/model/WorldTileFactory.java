package de.glooper.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.SaveStateManagement.Entities.TileSaveState;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.Screens.GameScreen.Heros.Hero;
import de.glooper.game.model.Tile.BorderSensor.CurrentTileSetter;
import de.glooper.game.model.Tile.BorderSensor.NewTileSetter;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.BorderSensor.TileBorderSensor;
import de.glooper.game.model.Tile.WorldTile;
import de.glooper.game.model.TileSets.ITileSet;
import de.glooper.game.model.TileSets.StarterTiles;

import java.util.Arrays;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTileFactory {
    private static final String TAG = WorldTileFactory.class.getSimpleName();


    private static WorldTileFactory instance;

    private IDynamicWorld dynamicWorld;
    private World world;
    private Hero hero;

    private ITileSet tileSet = new StarterTiles();

    private OrthographicCamera camera;



    private WorldTileFactory(OrthographicCamera camera, Hero hero ,IDynamicWorld dynamicWorld, World world) {
        this.camera = camera;
        this.hero = hero;
        this.dynamicWorld = dynamicWorld;
        this.world = world;
    }


    public static WorldTileFactory getInstance(OrthographicCamera camera,Hero hero, IDynamicWorld dynamicWorld, World world) {
        if (instance == null) {
                    instance = new WorldTileFactory(camera, hero, dynamicWorld, world);
        }
        return instance;
    }

    /**
     * this gets called when the game will be initialized
     * @return the start tile, which must have been loaded by
     * the assethandler firstly
     */
    public IWorldTile getStartTile(World world){
        WorldTile tile = new WorldTile(AssetHandler.instance.firstWorldTileAsset.getTexture(),
                "xCross",world,dynamicWorld, hero,0,0,0,0,tileSet.getNameOfSet(), camera);
        /*tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.DOWN,tile, hero, new NewTileSetter(dynamicWorld)));
        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.UP,tile, hero, new NewTileSetter(dynamicWorld)));
        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.LEFT,tile, hero, new NewTileSetter(dynamicWorld)));
        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.RIGHT,tile, hero, new NewTileSetter(dynamicWorld)));*/
        return tile;
    }

    /**
     *
     * @param x and
     * @param y are the coordinates of the new tile
     * @param direction is the side, that must be open
     *                  according to the direction, this method
     *                  should create a new WorldTile, that fits
     * @return
     */
    public IWorldTile getWorldTile(float x, float y, WorldTile.DIRECTION direction, IWorldTile parent){
        Gdx.app.log(TAG, String.valueOf(direction) +"sensor triggered");
        String[] tileNames;
        switch (direction){
            case DOWN:
                tileNames = tileSet.getBottomOpenings();
                break;
            case  UP:
                tileNames = tileSet.getTopOpenings();
                break;
            case RIGHT:
                tileNames = tileSet.getRightOpenings();
                break;
            case LEFT:
                tileNames = tileSet.getRightOpenings();
                break;
            default:
                tileNames =null;
            }
        System.out.println(Arrays.deepToString(tileNames));
        int randPick = MathUtils.random(0,tileNames.length-1);
        String pick = tileNames[randPick];

        IWorldTile tile = new WorldTile(AssetHandler.instance.firstWorldTileAsset.getTexture()
                ,pick,world,dynamicWorld, hero,x,y,0,0,tileSet.getNameOfSet(), camera);

       /* switch (direction){
            case DOWN:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT, tile, hero,new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT,tile, hero, new NewTileSetter(dynamicWorld)));
                //tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP, new CurrentTileSetter(dynamicWorld,tile)));
                break;
            case UP:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT,tile, hero, new NewTileSetter(dynamicWorld)));
                //tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN, new CurrentTileSetter(dynamicWorld,tile)));
                break;
            case LEFT:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT, tile, hero,new NewTileSetter(dynamicWorld)));
                //tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT, new CurrentTileSetter(dynamicWorld,tile)));
                break;
            case RIGHT:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP,tile, hero, new NewTileSetter(dynamicWorld)));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT,tile, hero, new NewTileSetter(dynamicWorld)));
                //tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT, new CurrentTileSetter(dynamicWorld,tile)));
                break;
        }*/
        return tile;
    }

    public IWorldTile createFromSaveState(TileSaveState saveState){
        IWorldTile tile = new WorldTile(AssetHandler.instance.firstWorldTileAsset.getTexture()
                ,saveState.tile.TAG,world,dynamicWorld, hero,
                saveState.tile.x,saveState.tile.y,
                0,0,saveState.nameOfSet, camera);
        return tile;
    }

    public static TileSaveState getStartTileEntity(){
        TileSaveState startTileEntity = new TileSaveState();
        startTileEntity.tile.TAG = "xCross";
        startTileEntity.nameOfSet = "starters";
        startTileEntity.tile.x = 0;
        startTileEntity.tile.y = 0;
        return startTileEntity;
    }

    //public void setComparator(Comparator<WorldTile> comp) {
      //  this.comp = comp;
    //}

}
