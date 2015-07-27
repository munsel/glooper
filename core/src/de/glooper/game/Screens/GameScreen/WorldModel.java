package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.Characters.Glooper;
import de.glooper.game.model.WorldTile;

/**
 * Created by munsel on 06.06.15.
 */
public class WorldModel implements Disposable{
    private static final String TAG = WorldModel.class.getSimpleName();

    private World world;
    private Glooper glooper;


    public WorldModel(){
        world = new World(new Vector2(0,0), false);
        glooper = new Glooper(world);
    }


    public void update(float delta){
        glooper.update(delta);
        world.step(1.f/60.f, 5, 5);
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

    public Array<Sprite> getAllSpritesToDraw(){
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(glooper.getSprite());
        return sprites;
    }


    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {

    }
}
