package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by munsel on 06.06.15.
 */
public class Renderer implements Disposable {

    private WorldModel model;
    //private Box2DDebugRenderer debugRenderer;
    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public Renderer(WorldModel model){
        this.model = model;
        camera = new OrthographicCamera(8f,4.8f);
        batch = new SpriteBatch();

        //debugRenderer = new Box2DDebugRenderer();
        world = model.getWorld();
    }

    public void render(float deltaTime){
        updateCameraPosition();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        //debugRenderer.render(world, camera.combined);

        batch.begin();
        for (Sprite sprite : model.getAllSpritesToDraw()){
            sprite.draw(batch);
        }
        batch.end();


    }

    /**
     * this gets the position of the hero
     * and sets the camera, so that the hero
     * never gets unattended
     */
    private void updateCameraPosition(){
        Vector3 heroPostion = model.getCameraPosition();
        camera.position.x = heroPostion.x;
        camera.position.x = heroPostion.y;

    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
