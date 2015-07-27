package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        //debugRenderer.render(world, camera.combined);

        batch.begin();
        for (Sprite sprite : model.getSprites()){
            sprite.draw(batch);
        }
        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
