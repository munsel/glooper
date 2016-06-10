package de.glooper.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.model.IDynamicWorld;

/**
 * Created by munsel on 12.08.15.
 */
public class DynamicTileWorld implements IDynamicWorld {
    private static final String TAG = DynamicTileWorld.class.getSimpleName();



    private Array<IWorldTile> tiles;
    private IWorldTile currentTile;


    private Array<Sprite> sprites;

    public static DynamicTileWorld instance;

    private Vector2 currentPos;

    private WorldTileFactory facory;
    private World world;
    private OrthographicCamera camera;

    private DynamicTileWorld(World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
        currentPos = new Vector2();
        tiles = new Array<IWorldTile>();
        init();
    }

    @Override
    public void init() {
        currentPos.setZero();
        facory = WorldTileFactory.getInstance(camera, this, world);
        tiles.add( facory.getStartTile(world) );
        currentTile = tiles.get(0);
    }

    @Override
    public void createNewTile(float x, float y, WorldTile.DIRECTION direction) {
        currentPos.x = x;
        currentPos.y = y;

        int currentIndex = tiles.indexOf(currentTile, false);
        for(int i = 0; i < tiles.size; i++){
            if (i == currentIndex)continue;
            tiles.removeIndex(i);
        }
        /*
        if (tiles.size>=2){
            for (IWorldTile tile: tiles){
                if (!tile.equals(currentTile)){
                    tiles.removeValue(tile, false);
                }
            }
        }*/
        tiles.add(facory.getWorldTile(x, y, direction));
    }

    @Override
    public void setCurrentTile(IWorldTile newCurrentTile) {
        newCurrentTile = newCurrentTile;
    }


    public static IDynamicWorld getInstance(World world, OrthographicCamera camera) {
        if (instance == null)
            instance = new DynamicTileWorld(world, camera);

        return instance;
    }



    @Override
    public void draw(SpriteBatch batch) {
        for (IWorldTile tile: tiles){
            tile.draw(batch);
        }

    }

    @Override
    public Vector2 getCurrentPos(){
        return currentPos;
    }

    @Override
    public String getCurrentName() {
        return currentTile.getName();
    }

    @Override
    public void update(float delta) {
        currentTile.update(delta);
    }

    @Override
    public void drawDebugSensors(ShapeRenderer shapeRenderer){
        for (IWorldTile tile: tiles){
            tile.drawDrebugSensors(shapeRenderer);
        }

    }
}
