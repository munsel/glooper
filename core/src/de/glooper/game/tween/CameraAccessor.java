package de.glooper.game.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by munsel on 12.11.14.
 */
public class CameraAccessor implements TweenAccessor<OrthographicCamera> {
    public static final int XY_MOVE = 0;
    public static final int X_MOVE = 1;
    public static final int Y_MOVE = 2;
    public static final int ZOOM = 3;




    @Override
    public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case XY_MOVE:
                returnValues[0] = target.position.x;
                returnValues[1] = target.position.y;
                return 2;
            case X_MOVE:
                returnValues[0] = target.position.x;
                return 1;
            case Y_MOVE:
                returnValues[0] = target.position.y;
                return 1;
            case ZOOM:
                returnValues[0] = target.zoom;
                return 1;
            default:
                assert false;
                return -1;

        }

    }

    @Override
    public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
        switch (tweenType){
            case XY_MOVE:
                target.position.x = newValues[0];
                target.position.y = newValues[1];
                break;
            case X_MOVE:
                target.position.x = newValues[0];
                break;
            case Y_MOVE:
                target.position.y = newValues[0];
                break;
            case ZOOM:
                target.zoom = newValues[0];
                break;
            default:
                assert false;
                break;

        }
        target.update();

    }
}
