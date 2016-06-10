package de.glooper.game.SaveStateManagement;

import com.badlogic.gdx.utils.Array;
import de.glooper.game.model.IWorldTile;
import de.glooper.game.model.WorldTile;

import java.io.Serializable;

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
    private Array<IWorldTile> tiles;

    //private static SaveState instance = new SaveState();
    public  SaveState(){}

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
        heroX = WorldTile.TILE_SIZE/2;
        heroY = WorldTile.TILE_SIZE/2;
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

}
