package de.glooper.game.SaveStateManagement;

import com.badlogic.gdx.utils.Array;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.SaveStateManagement.Entities.TileSaveState;
import de.glooper.game.model.Tile.WorldTile;
import de.glooper.game.model.TileWorld.WorldTileFactory;

/**
 * Created by munsel on 19.09.15.
 */
public class SaveState  {


    private String theme;
    private boolean musicOn;
    private boolean soundsOn;
    private float musicVolume;
    private float soundVolume;

    private boolean gameOver;

    private int frames;
    private int score;

    private float heroX, heroY, heroRot;



    private float heroStamina;
    //private Array<IWorldTile> tiles;
    private Array<TileSaveState> tiles;

    private EntitySaveState cameraData;

    //private static SaveState instance = new SaveState();
    public  SaveState(){
        tiles = new Array<TileSaveState>();
        cameraData = new EntitySaveState();
    }

    /* public static  SaveState getInstance(){
        if (instance == null){
            instance = new SaveState();
        }
        return instance;
    }*/
    public void reset(){
        gameOver = false;
        frames = 0;
        score = 0;
        heroRot = 0;
        heroX = WorldTile.TILE_SIZE/5;
        heroY = WorldTile.TILE_SIZE/4;
        heroStamina = 1;
        cameraData.x = heroX;
        cameraData.y = heroY;
        tiles.clear();
        tiles.add(WorldTileFactory.getStartTileEntity());
    }

    public int addToScore(int inc){return score+=inc;}

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public float getHeroRot() {
        return heroRot;
    }

    public void setHeroRot(float heroRot) {
        this.heroRot = heroRot;
    }

    public float getHeroX() {
        return heroX;
    }

    public void setHeroX(float heroX) {
        this.heroX = heroX;
    }

    public float getHeroY() {
        return heroY;
    }

    public void setHeroY(float heroY) {
        this.heroY = heroY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public float getHeroStamina() {
        return heroStamina;
    }

    public void setHeroStamina(float heroStamina) {
        this.heroStamina = heroStamina;
    }

    public  Array<TileSaveState> getTiles() {
        return tiles;
    }

    public void setTiles(Array<TileSaveState> tiles) {
        this.tiles = tiles;
    }

    public EntitySaveState getCameraData() {
        return cameraData;
    }

    public void setCameraData(EntitySaveState cameraData) {
        this.cameraData = cameraData;
    }

/*
    public void setTiles(Array<IWorldTile> tiles) {
        this.tiles = tiles;
    }

    public Array<IWorldTile> getTiles() {
        return tiles;
    }*/


}
