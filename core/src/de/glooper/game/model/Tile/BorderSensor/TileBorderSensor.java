package de.glooper.game.model.Tile.BorderSensor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import de.glooper.game.model.Heros.Hero;
import de.glooper.game.Screens.GameScreen.WorldModel;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.WorldTile;

/**
 * Created by munsel on 23.08.15.
 * TODO: this is ugly as shit, bad smell bad smell!
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

    private ITileBorderSensorBehaviour behaviour;
    private IWorldTile parent;
    private Hero hero;



    private float x, y, x1, y1;


    public TileBorderSensor( float x, float y, WorldTile.DIRECTION direction,
                             IWorldTile parentTile, Hero hero,
                             ITileBorderSensorBehaviour behaviour){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.parent = parentTile;
        this.hero = hero;
        this.behaviour = behaviour;
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
                    if (cameraPosition.x < x1 && cameraPosition.x > x) {
                        if (cameraPosition.y - (WorldModel.VIEWPORT_Y * 0.5f) - checkValue <= 0) {
                            behaviour.update(nextX, nextY, direction);
                            checked = true;
                        }
                    }
                } else if (direction == WorldTile.DIRECTION.UP) {
                    if (cameraPosition.x < x1 && cameraPosition.x > x) {
                        if (checkValue - (cameraPosition.y + (WorldModel.VIEWPORT_Y * 0.5f)) <= 0) {
                            behaviour.update(nextX, nextY, direction);
                            checked = true;
                        }
                    }
                } else if (direction == WorldTile.DIRECTION.LEFT) {
                    if (cameraPosition.y < y1 && cameraPosition.y > y) {
                        if (cameraPosition.x - (WorldModel.VIEWPORT_X * 0.5f) - checkValue <= 0) {
                            behaviour.update(nextX, nextY, direction);
                            checked = true;
                        }
                    }

                } else if (direction == WorldTile.DIRECTION.RIGHT) {
                    if (cameraPosition.y < y1 && cameraPosition.y > y) {
                        if (checkValue - cameraPosition.x - (WorldModel.VIEWPORT_X * 0.5f) <= 0) {
                            behaviour.update(nextX, nextY, direction);
                            checked = true;
                        }
                    }

                }
        }

    }

    public void setBehaviour(ITileBorderSensorBehaviour behaviour){
        this.behaviour = behaviour;
    }

    public void uncheck(){
        checked = false;
    }


    public void drawDebugSensors(ShapeRenderer shapeRenderer){
        shapeRenderer.line(x,y,x1,y1);
    }


}
