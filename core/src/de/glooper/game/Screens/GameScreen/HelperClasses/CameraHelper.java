package de.glooper.game.Screens.GameScreen.HelperClasses;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.SaveStateManagement.Safeable;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.Screens.GameScreen.WorldModel;
import de.glooper.game.model.Tile.WorldTile;

import java.math.BigInteger;


/**
 * Created by munsel on 14.08.15.
 */
public class CameraHelper implements Safeable{
    private static final String TAG = CameraHelper.class.getSimpleName();

    private final float CAMERA_OFFSET = 8f;

    private WorldModel model;
    private  OrthographicCamera camera;

    private float stateTime;

    private Vector2 integral;
    private final float integralFactor = 0.01f;
    /**
     * Lerp model for smooth camera movements
     *
     */
    private static float lerp = .3f;

    private Vector2 initFocusPoint;

    public CameraHelper(WorldModel model){
        this.model = model;
        this.camera = model.getCamera();    
        //tweenManager = new TweenManager();
        integral = new Vector2();
        stateTime = 0;
        //Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
        initFocusPoint = new Vector2(camera.position.x, camera.position.y);
    }
/*
    public void startTweening(){
        Gdx.app.log(TAG, "started tweening");
        heroPos = model.getHeroPosition();
        Tween.set(camera,CameraAccessor.XY_MOVE).target(camera.position.x, camera.position.y).start(tweenManager);
        Tween.to(camera, CameraAccessor.XY_MOVE, 0.5f).target(heroPos.x, heroPos.y).
                ease(TweenEquations.easeInOutSine).
                setCallback(tweenCameraAgain).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);

    }

    TweenCallback tweenCameraAgain = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            if(isTweening){
                startTweening();
            }

        }
    };
*/

    /**
     * gets a vector that points from the following pos to the followed pos
     * both positions not included.
     * @param followMe
     * @param following
     * @param delta
     * @return
     */
    public static Vector2 lerp(Vector2 followMe, Vector2 following, float delta){
        Vector2 a = followMe.sub(following);
        a.scl(lerp*delta);
        return a;
    }

    private Vector2 getCameraFocusPosition(){
        /*boolean heroUpperSide = model.getHero().getPosition().y+2 > camera.position.y+camera.viewportHeight/2;
        boolean heroLowerSide = model.getHero().getPosition().y-2 < camera.position.y-camera.viewportHeight/2;
        */

        float heroRotation = model.getHero().getRotation();
        Vector2 heroLookingAt = new Vector2(
                MathUtils.cos(heroRotation),
                MathUtils.sin(heroRotation)
        );

        Vector2 result = new Vector2();

        /*boolean heroIsPointingUp = heroLookingAt.y > 0.9;
        boolean heroIsPointingDown = heroLookingAt.y < -0.9;
        if (heroUpperSide || heroIsPointingUp){
            result.y =  - CAMERA_OFFSET;
        }
        else if(heroLowerSide||heroIsPointingDown){
            result.y = CAMERA_OFFSET;
        }
        else{
            result.y = 0;
        }*/
        result.y = (-heroLookingAt.y)*CAMERA_OFFSET;
        //result.x = 0;
        result.x = (-heroLookingAt.x)*CAMERA_OFFSET;

        integral.x += (-heroLookingAt.x)*integralFactor;
        integral.y += (-heroLookingAt.y)*integralFactor;
        if (integral.x>CAMERA_OFFSET) integral.x = CAMERA_OFFSET;
        if (integral.x<-CAMERA_OFFSET) integral.x = -CAMERA_OFFSET;
        if (integral.y>CAMERA_OFFSET) integral.y = CAMERA_OFFSET;
        if (integral.y<-CAMERA_OFFSET) integral.y = -CAMERA_OFFSET;
        return result;
    }


    public void update(float delta){
        //tweenManager.update(delta);

        Vector2 actualCameraPos = new Vector2(camera.position.x,camera.position.y);
        Vector2 desiredCameraPos =  actualCameraPos.add(initFocusPoint  );
        //desiredCameraPos = lerp(desiredCameraPos, actualCameraPos, delta);
        initFocusPoint.set(getCameraFocusPosition());
        stateTime += delta;
        if (stateTime > 1){
            stateTime = 0;

        }
        /**
         * camera following heroPos
         */

        Vector2 lerpTempy =
            lerp(model.getHero().getPosition(),desiredCameraPos,delta);

        camera.position.x = camera.position.x+lerpTempy.x;
        camera.position.y = camera.position.y+lerpTempy.y;

        camera.update();
    }

    @Override
    public void save(SaveState saveState) {
        EntitySaveState entitySaveState = new EntitySaveState();
        entitySaveState.x = camera.position.x;
        entitySaveState.y = camera.position.y;
        saveState.setCameraData(entitySaveState);
    }

    @Override
    public void load(SaveState saveState) {
        EntitySaveState cameraData = saveState.getCameraData();
        if (cameraData != null){
            camera.position.x = cameraData.x;
            camera.position.y = cameraData.y;
        }else{
            camera.position.x = WorldTile.TILE_SIZE/2;
            camera.position.y = WorldTile.TILE_SIZE/2;
        }
        camera.update();
    }
}
