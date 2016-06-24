package de.glooper.game.SaveStateManagement.Entities;

import com.badlogic.gdx.utils.Array;

/**
 * Created by munsel on 22.06.16.
 */
public class TileSaveState {

    public String nameOfSet;
    public EntitySaveState tile;
    public Array<EntitySaveState> obstacles;
    public TileSaveState(){
        tile = new EntitySaveState();
        obstacles = new Array<EntitySaveState>();
    }

}
