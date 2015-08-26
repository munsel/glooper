package de.glooper.game.model;

import com.badlogic.gdx.math.Vector3;
import de.glooper.game.Screens.GameScreen.WorldModel;

/**
 * Created by munsel on 23.08.15.
 */
public class TileBorderSensor {
    private static final String TAG = TileBorderSensor.class.getSimpleName();

    private static  final float SENSOR_PADDING = 1f;

    private enum ORIENTATION{HORIZONTAL, VERTICAL}

    private ORIENTATION orientation;

    private float x,y;

    private float checkValue;


    private WorldTile.DIRECTION direction;



    private float nextX, nextY;

    private WorldTileFactory factory;


    public TileBorderSensor( float x, float y, WorldTile.DIRECTION direction, WorldTileFactory factory){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.factory = factory;

        if (direction == WorldTile.DIRECTION.DOWN){
            nextX = x;
            nextY = y - WorldTile.TILE_SIZE;
            checkValue = y + SENSOR_PADDING;
            orientation = ORIENTATION.VERTICAL;
        }else if (direction == WorldTile.DIRECTION.UP){
            nextX = x;
            nextY = y + WorldTile.TILE_SIZE;
            checkValue = y + WorldTile.TILE_SIZE - SENSOR_PADDING;
            orientation = ORIENTATION.VERTICAL;
        }else if (direction == WorldTile.DIRECTION.LEFT){
            nextX = x - WorldTile.TILE_SIZE;
            nextY = y;
            checkValue = x + SENSOR_PADDING;
            orientation = ORIENTATION.HORIZONTAL;
        }else if (direction == WorldTile.DIRECTION.RIGHT){
            nextX = x + WorldTile.TILE_SIZE;
            nextY = y;
            checkValue = x + WorldTile.TILE_SIZE - SENSOR_PADDING;
            orientation = ORIENTATION.HORIZONTAL;
        }



    }

    public void update(Vector3 cameraPosition){
        if (direction == WorldTile.DIRECTION.DOWN){
            if ( cameraPosition.y - (WorldModel.VIEWPORT_Y*0.5f) - checkValue<=0 ){

            }
        }else if (direction == WorldTile.DIRECTION.UP){
            if (checkValue - cameraPosition.y+(WorldModel.VIEWPORT_Y*0.5f) <=0 ){

            }

        }else if (direction == WorldTile.DIRECTION.LEFT){
            if (cameraPosition.x - (WorldModel.VIEWPORT_X*0.5f) - checkValue <= 0){

            }

        }else if (direction == WorldTile.DIRECTION.RIGHT){
            if (checkValue- cameraPosition.x + (WorldModel.VIEWPORT_X*0.5f )<= 0){

            }

        }

    }


}
