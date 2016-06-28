package de.glooper.game.model.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.model.Tile.IWorldTile;

import javax.xml.soap.SAAJMetaFactory;

/**
 * Created by munsel on 27.06.16.
 */
public class EntityFactory {
    private static final String TAG = EntityFactory.class.getSimpleName();

    private final static float SEAWEED_WIDTH=.3f;
    private final static float SEAWEED_HEIGHT=.6f;

    public static IEntity getEntity(IWorldTile parent, World world, EntitySaveState saveState){
        Entity entity;
        Gdx.app.log(TAG, saveState.TAG);
        if (saveState.TAG.equals("seaweed")){
            entity = new Entity(world, AssetHandler.instance.seaweed,
                    "seaweed",
                    parent.getX()+saveState.x,
                    parent.getY()+saveState.y );
            entity.setWidth(SEAWEED_WIDTH);
            entity.setHeight(SEAWEED_HEIGHT);
            Gdx.app.log(TAG, "created seaweed!");
            Gdx.app.log(TAG, Float.toString(parent.getX()+saveState.x));
            Gdx.app.log(TAG, Float.toString(parent.getX()+saveState.y));
        }else{
            entity = new Entity(world, AssetHandler.instance.seaweed,
                    "enemy",
                    parent.getX()+saveState.x,
                    parent.getY()+saveState.y );
        }
        return entity;
    }




    public static IEntity getLittleEnemy(World world, EntitySaveState saveState){
        return new Entity(world, null,null, saveState.x, saveState.y );
    }
}
