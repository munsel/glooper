package de.glooper.game.model.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.model.Physics.Box2DCategoryBits;
import de.glooper.game.model.Tile.IWorldTile;

import javax.xml.soap.SAAJMetaFactory;

/**
 * Created by munsel on 27.06.16.
 */
public class EntityFactory {
    private static final String TAG = EntityFactory.class.getSimpleName();

    private final static float SEAWEED_WIDTH=.3f;
    private final static float SEAWEED_HEIGHT=.6f;

    private final static float EEL_WIDTH=.8f;
    private final static float EEL_HEIGHT=.2f;


/*
    public static IEntity getEntity(IWorldTile parent, World world, EntitySaveState saveState){
        Entity entity;
        Gdx.app.log(TAG, saveState.TAG);
        if (saveState.TAG.equals("seaweed")){
            entity = new Entity(parent,world, AssetHandler.instance.seaweed,
                    "seaweed",
                    parent.getX()+saveState.x,
                    parent.getY()+saveState.y,
                    SEAWEED_WIDTH, SEAWEED_HEIGHT,
                    Box2DCategoryBits.FOOD);
            Gdx.app.log(TAG, "created seaweed!");
            Gdx.app.log(TAG, Float.toString(parent.getX()+saveState.x));
            Gdx.app.log(TAG, Float.toString(parent.getX()+saveState.y));
            entity.setBehaviour(new FoodBehaviour(entity));
        }else if (saveState.TAG.equals("eel")){
            entity = new Entity(parent, world, AssetHandler.instance.eel,"eel",
                    parent.getX()+saveState.x,
                    parent.getY()+saveState.y,
                    EEL_WIDTH, EEL_HEIGHT,
                    Box2DCategoryBits.ENEMY);
            entity.setBehaviour(new EelBehaviour(entity));
        } else{
            entity = new Entity(parent,world, AssetHandler.instance.seaweed,
                    "enemy",
                    parent.getX()+saveState.x,
                    parent.getY()+saveState.y,
                    SEAWEED_WIDTH, SEAWEED_HEIGHT,
                    Box2DCategoryBits.FOOD);
            entity.setBehaviour(new FoodBehaviour(entity));
        }
        return entity;
    }*/

}
