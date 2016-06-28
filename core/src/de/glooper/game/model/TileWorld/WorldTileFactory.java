package de.glooper.game.model.TileWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.SaveStateManagement.Entities.TileSaveState;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.model.Heros.Hero;
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
    public WorldTile getStartTile(World world){
        WorldTile tile = new WorldTile(AssetHandler.instance.firstWorldTileAsset.getTexture(),
                "LRBU",world,dynamicWorld, hero,0,0,0,0,tileSet.getNameOfSet(), camera);
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
    public WorldTile getWorldTile(float x, float y, WorldTile.DIRECTION direction, WorldTile parent){
        Gdx.app.log(TAG, String.valueOf(direction) +"sensor triggered");
        String[] tileNames;
        switch (direction){
            case DOWN:
                tileNames = tileSet.getTopOpenings();
                break;
            case  UP:
                tileNames = tileSet.getBottomOpenings();
                break;
            case RIGHT:
                tileNames = tileSet.getLeftOpenings();
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
        Gdx.app.log(TAG, pick);

        WorldTile tile = new WorldTile(AssetHandler.instance.tileAssets.get(pick).getTexture()
                ,pick,world,dynamicWorld, hero,x,y,0,0,tileSet.getNameOfSet(), camera);
        tile.setEntityState(tile.getInitialState());
        return tile;
    }

    public WorldTile createFromSaveState(TileSaveState saveState){
        WorldTile tile = new WorldTile(AssetHandler.instance.tileAssets.get(saveState.tile.TAG).getTexture()
                ,saveState.tile.TAG,world,dynamicWorld, hero,
                saveState.tile.x,saveState.tile.y,
                0,0,saveState.nameOfSet, camera);
        tile.setEntityState(saveState);
        return tile;
    }

    public static TileSaveState getStartTileEntity(){
        return getInitialState("starters/LRBU");
    }


    /**
     * loads items/enemies from
     * json file, which is in the same folder
     * as the texture assets
     */
    public static TileSaveState getInitialState(String fileName){
        TileSaveState initialState;
        Json json = new Json();
        try {
            FileHandle handle = Gdx.files.local("WorldTiles/"+fileName+".json");
            String fileContent = handle.readString();
            //System.out.println(json.prettyPrint(fileContent));
            initialState = json.fromJson(TileSaveState.class, fileContent);
        } catch (SerializationException e){
            Gdx.app.error(TAG, "could not read the JSON file!");
            initialState = new TileSaveState();
        } catch (GdxRuntimeException e){
            Gdx.app.error(TAG, "file not found YO!");
            initialState = new TileSaveState();
            initialState.nameOfSet = "starters";
            EntitySaveState defaultSaveState = new EntitySaveState();
            defaultSaveState.TAG = "seaweed";
            defaultSaveState.x = 7;
            defaultSaveState.y = 9;
            initialState.entities.add(defaultSaveState);
        }
        return initialState;
    }

    //public void setComparator(Comparator<WorldTile> comp) {
      //  this.comp = comp;
    //}

}
