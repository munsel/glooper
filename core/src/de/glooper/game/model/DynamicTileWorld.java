package de.glooper.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.SaveStateManagement.Entities.TileSaveState;
import de.glooper.game.SaveStateManagement.Safeable;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.Screens.GameScreen.Heros.Hero;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.WorldTile;

import java.util.ArrayList;

/**
 * Created by munsel on 12.08.15.
 */
public class DynamicTileWorld implements IDynamicWorld, Safeable {
    private static final String TAG = DynamicTileWorld.class.getSimpleName();

    private Array<IWorldTile> tiles;
    private IWorldTile currentTile;

    public static DynamicTileWorld instance;

    private Vector2 currentPos;

    private WorldTileFactory facory;
    private World world;
    private Hero hero;
    private OrthographicCamera camera;
    private SaveState saveState;

    private DynamicTileWorld(World world, Hero hero, OrthographicCamera camera, SaveState saveState){
        this.world = world;
        this.camera = camera;
        this.hero = hero;
        this.saveState = saveState;
        currentPos = new Vector2();
        tiles = new Array<IWorldTile>();
        facory = WorldTileFactory.getInstance(camera, hero,this, world);
    }

    @Override
    public void init() {
        currentPos.setZero();
        tiles.clear();
        load(saveState);
        //currentTile = tiles.get(0);
    }

    @Override
    public void createNewTile(float x, float y, WorldTile.DIRECTION direction) {
        currentPos.x = x;
        currentPos.y = y;

        int currentIndex = tiles.indexOf(currentTile, true);
        for(int i = 0; i < tiles.size; i++){
            if (i == currentIndex)continue;
            tiles.get(i).removeBody();
            tiles.removeIndex(i);
        }
/*
        if (tiles.size>=2){
            for (IWorldTile tile: tiles){
                if (!tile.equals(currentTile)){
                    tiles.removeValue(tile, true);
                    tile.removeBody();
                }
            }
        }*/
        tiles.add(facory.getWorldTile(x, y, direction, currentTile));
    }

    @Override
    public void setCurrentTile(IWorldTile newCurrentTile) {
        currentTile = newCurrentTile;
    }

    @Override
    public IWorldTile getCurrentTile() {
        return currentTile;
    }

    public static DynamicTileWorld getInstance(World world, Hero hero,OrthographicCamera camera, SaveState state) {
        if (instance == null)
            instance = new DynamicTileWorld(world,hero, camera, state);

        return instance;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (IWorldTile tile: tiles){
            tile.draw(batch);
        }

    }

    @Override
    public String getCurrentName() {
        return currentTile.getName();
    }

    @Override
    public void update(float delta) {
        for (IWorldTile tile: tiles)
        tile.update(delta);
    }

    @Override
    public void drawDebugSensors(ShapeRenderer shapeRenderer){
        for (IWorldTile tile: tiles){
            tile.drawDrebugSensors(shapeRenderer);
        }

    }

    @Override
    public void save(SaveState saveState) {
        Array<TileSaveState> savedTiles = new Array<TileSaveState>();
        for (IWorldTile tile : tiles) {
            TileSaveState tileSaveState = new TileSaveState();
            tileSaveState.nameOfSet = tile.getNameOfSet();
            tileSaveState.tile.TAG = tile.getName();
            tileSaveState.tile.x = tile.getX();
            tileSaveState.tile.y = tile.getY();
            savedTiles.add(tileSaveState);
        }
        saveState.setTiles(savedTiles);
    }

    @Override
    public void load(SaveState saveState) {
        if(saveState.getTiles()!=null) {
            Array<TileSaveState> savedTiles = saveState.getTiles();
            tiles.clear();
            for (TileSaveState tileSaveState : savedTiles){
                IWorldTile tile = facory.createFromSaveState(tileSaveState);
                tiles.add(tile);
            }
        }
        else {
            tiles.add( facory.getStartTile(world) );
            Gdx.app.log(TAG, "could not load tileset!");
        }
    }
}
