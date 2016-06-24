package de.glooper.game.Screens.GameScreen.HelperClasses;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import de.glooper.game.SaveStateManagement.Entities.EntitySaveState;
import de.glooper.game.SaveStateManagement.Safeable;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.Screens.GameScreen.WorldModel;
import de.glooper.game.model.Tile.WorldTile;


/**
 * Created by munsel on 14.08.15.
 */
public class CameraHelper implements Safeable{
    private static final String TAG = CameraHelper.class.getSimpleName();

    private WorldModel model;
    private  OrthographicCamera camera;

    private Vector2 heroPos;

    /**
     * Lerp model for smooth camera movements
     *
     */
    private float lerp = 3f;

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
        heroPos = model.getHero().getPosition();
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
