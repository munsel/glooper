package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.HelperClasses.CameraHelper;
import de.glooper.game.Screens.GameScreen.Heros.Glooper;
import de.glooper.game.Screens.GameScreen.Heros.IHero;
import de.glooper.game.model.DynamicTileWorld;
import de.glooper.game.model.IDynamicWorld;
import de.glooper.game.model.WorldTile;
import de.glooper.game.model.WorldTileFactory;

/**
 * Created by munsel on 06.06.15.
 */
public class WorldModel implements Disposable {
    private static final String TAG = WorldModel.class.getSimpleName();


    public static final float VIEWPORT_X = 8f;
    public static final float VIEWPORT_Y = 4.8f;


    private World world;
    private Glooper glooper;
    private IDynamicWorld dynamicTileWorld;
    private OrthographicCamera camera;
    private CameraHelper cameraHelper;


    public WorldModel(){
        camera = new OrthographicCamera(VIEWPORT_X, VIEWPORT_Y);
        //camera.position.x = WorldTile.TILE_SIZE/2;
        //camera.position.y = 0.5f*WorldTile.TILE_SIZE/2;





        world = new World(new Vector2(0,0), false);
        dynamicTileWorld = DynamicTileWorld.getInstance(world);
        glooper = new Glooper(world);

        cameraHelper = new CameraHelper(this);
        //cameraHelper.startTweening();
    }


    public void update(float delta){
        glooper.update(delta);
        world.step(1.f / 60.f, 5, 5);
        cameraHelper.update(delta);
    }

    public void setGlooperWobblieness(float omega, float a){
        glooper.setWiggleAmplitude(a);
        glooper.setAngularVelocity(omega);
    }
    public float getGlooperWobblienessFreqency(){
        return glooper.getWiggleFrequency();
    }
    public float getGlooperWobblienessAmplitude(){
        return glooper.getWiggleAmplitude();
    }


    public void glooperRight(){
        glooper.setAngularVelocity(glooper.getAbsoluteOmega());
    }
    public void glooperLeft(){
        glooper.setAngularVelocity( -(glooper.getAbsoluteOmega()) );
    }

    public OrthographicCamera getCamera(){return camera;}
    public Array<Sprite> getBackgroundSpritesToDraw(){
        Array<Sprite> sprites = new Array<Sprite>();


        for (Sprite sprite : dynamicTileWorld.getSprites()) {
            sprites.add(sprite);
        }

        return sprites;
    }

    public Sprite getHeroSprite(){
        return glooper.getSprite();
    }

    public Vector2 getHeroPosition(){
        return glooper.getPosition();
    }






    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {

    }
}
