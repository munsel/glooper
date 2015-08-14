package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

        camera = model.getCamera();
        batch = new SpriteBatch();

        //debugRenderer = new Box2DDebugRenderer();
        world = model.getWorld();
    }

    public void render(float deltaTime){

        batch.setProjectionMatrix(camera.combined);
        //debugRenderer.render(world, camera.combined);

        batch.begin();
        for (Sprite sprite : model.getBackgroundSpritesToDraw()){
            sprite.draw(batch);
        }
        model.getHeroSprite().draw(batch);
        batch.end();
        debugRenderings();


    }

    private void debugRenderings(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 heroPos = model.getHeroPosition();
        shapeRenderer.point(heroPos.x, heroPos.y, 0);

        Vector3 cameraPos = model.getCamera().position;
        shapeRenderer.point(cameraPos.x, cameraPos.y, 0);

        shapeRenderer.end();
    }




    @Override
    public void dispose() {
        batch.dispose();

    }
}
