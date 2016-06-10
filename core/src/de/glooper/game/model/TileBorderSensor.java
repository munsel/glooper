package de.glooper.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    private float checkValue;

    private boolean checked;


    private WorldTile.DIRECTION direction;



    private float nextX, nextY;

    private IDynamicWorld dynamicWorld;
    private float x, y, x1, y1;


    public TileBorderSensor( float x, float y, WorldTile.DIRECTION direction, IDynamicWorld world){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.dynamicWorld = world;
        checked = false;



        if (direction == WorldTile.DIRECTION.DOWN){
            nextX = x;
            nextY = y - WorldTile.TILE_SIZE;
            checkValue = y + SENSOR_PADDING;
            orientation = ORIENTATION.VERTICAL;
            this.x = x;
            this.y = checkValue;
            this.x1 = x+ WorldTile.TILE_SIZE;
            this.y1 = checkValue;
        }else if (direction == WorldTile.DIRECTION.UP){
            nextX = x;
            nextY = y + WorldTile.TILE_SIZE;
            checkValue = y + WorldTile.TILE_SIZE - SENSOR_PADDING;
            orientation = ORIENTATION.VERTICAL;
            this.x = x;
            this.y = checkValue;
            this.x1 = x+ WorldTile.TILE_SIZE;
            this.y1 = checkValue;
        }else if (direction == WorldTile.DIRECTION.LEFT){
            nextX = x - WorldTile.TILE_SIZE;
            nextY = y;
            checkValue = x + SENSOR_PADDING;
            orientation = ORIENTATION.HORIZONTAL;
            this.x = checkValue;
            this.y = y;
            this.x1 = checkValue;
            this.y1 = y + WorldTile.TILE_SIZE;
        }else if (direction == WorldTile.DIRECTION.RIGHT){
            nextX = x + WorldTile.TILE_SIZE;
            nextY = y;
            checkValue = x + WorldTile.TILE_SIZE - SENSOR_PADDING;
            orientation = ORIENTATION.HORIZONTAL;
            this.x = checkValue;
            this.y = y;
            this.x1 = checkValue;
            this.y1 = y + WorldTile.TILE_SIZE;
        }



    }

    public void update(Vector3 cameraPosition){
        //Gdx.app.log(TAG,String.valueOf(direction)+" checkvalue is: "+String.valueOf(checkValue));
        if(!checked) {
            if (direction == WorldTile.DIRECTION.DOWN) {
                if (cameraPosition.y - (WorldModel.VIEWPORT_Y * 0.5f) - checkValue <= 0) {
                    dynamicWorld.createNewTile(nextX, nextY, direction);
                    checked = true;
                }
            } else if (direction == WorldTile.DIRECTION.UP) {
                if (checkValue - (cameraPosition.y + (WorldModel.VIEWPORT_Y * 0.5f)) <= 0) {
                    dynamicWorld.createNewTile(nextX, nextY, direction);
                    checked = true;
                }
            } else if (direction == WorldTile.DIRECTION.LEFT) {
                if (cameraPosition.x - (WorldModel.VIEWPORT_X * 0.5f) - checkValue <= 0) {
                    dynamicWorld.createNewTile(nextX, nextY, direction);
                    checked = true;
                }

            } else if (direction == WorldTile.DIRECTION.RIGHT) {
                if (checkValue - cameraPosition.x - (WorldModel.VIEWPORT_X * 0.5f) <= 0) {
                    dynamicWorld.createNewTile(nextX, nextY, direction);
                    checked = true;
                }

            }
        }

    }

    public void uncheck(){
        checked = false;
    }


    public void drawDrebugSensors(ShapeRenderer shapeRenderer){
        shapeRenderer.line(x,y,x1,y1);
    }


}
