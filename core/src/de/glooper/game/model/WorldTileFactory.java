package de.glooper.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.model.TileSets.ITileSet;
import de.glooper.game.model.TileSets.StarterTiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vincent on 18.07.15.
 */
public class WorldTileFactory {
    private static final String TAG = WorldTileFactory.class.getSimpleName();


    private static WorldTileFactory instance;

    private IDynamicWorld dynamicWorld;
    private World world;

    private ITileSet tileSet = new StarterTiles();

    private OrthographicCamera camera;



    private WorldTileFactory(OrthographicCamera camera, IDynamicWorld dynamicWorld, World world) {
        this.camera = camera;
        this.dynamicWorld = dynamicWorld;
        this.world = world;
        AssetHandler.instance.init(new AssetManager());

    }
    public static WorldTileFactory getInstance(OrthographicCamera camera, IDynamicWorld dynamicWorld, World world) {
        if (instance == null) {

                    instance = new WorldTileFactory(camera, dynamicWorld, world);

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
                "xCross",world,0,0,0,0,tileSet.getSetName(), camera);

        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.DOWN, dynamicWorld));
        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.UP, dynamicWorld));
        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.LEFT, dynamicWorld));
        tile.attachSensor(new TileBorderSensor(0,0, WorldTile.DIRECTION.RIGHT, dynamicWorld));
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
    public IWorldTile getWorldTile(float x, float y, WorldTile.DIRECTION direction){
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
                ,pick,world, x,y,0,0,tileSet.getSetName(), camera);

        switch (direction){
            case DOWN:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT, dynamicWorld));
                break;
            case UP:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT, dynamicWorld));
                break;
            case LEFT:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.LEFT, dynamicWorld));
                break;
            case RIGHT:
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.DOWN, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.UP, dynamicWorld));
                tile.attachSensor(new TileBorderSensor(x,y, WorldTile.DIRECTION.RIGHT, dynamicWorld));
                break;
        }




        return tile;
    }

    //public void setComparator(Comparator<WorldTile> comp) {
      //  this.comp = comp;
    //}

}
