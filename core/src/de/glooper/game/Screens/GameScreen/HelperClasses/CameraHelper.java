package de.glooper.game.Screens.GameScreen.HelperClasses;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.glooper.game.Screens.GameScreen.WorldModel;
import de.glooper.game.tween.CameraAccessor;

/**
 * Created by munsel on 14.08.15.
 */
public class CameraHelper {
    private static final String TAG = CameraHelper.class.getSimpleName();
    public boolean isTweening = true;

    private WorldModel model;
    private  OrthographicCamera camera;
    private TweenManager tweenManager;
    private Vector2 heroPos;

    /**
     * Lerp model for smooth camera movements
     *
     */
    private float lerp = 1f;



    public CameraHelper(WorldModel model){
        this.model = model;
        this.camera = model.getCamera();
        //tweenManager = new TweenManager();
        heroPos = new Vector2();
        //Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());


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
    private void lerp(float delta){
        Vector2 pC = new Vector2(camera.position.x, camera.position.y);
        heroPos = model.getHeroPosition();
        Vector2 a = heroPos.sub(pC);
        a.scl(lerp*delta);
        camera.position.x = camera.position.x+a.x;
        camera.position.y = camera.position.y+a.y;

    }
    public void update(float delta){
        //tweenManager.update(delta);
        lerp(delta);
        camera.update();


    }

}
