package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.Heros.IHero;

/**
 * Created by munsel on 06.06.15.
 */
public class Renderer implements Disposable {

    private WorldModel model;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer debugShapeRenderer;

    private IHero hero;

    public Renderer(WorldModel model){
        this.model = model;
        this.hero = model.getHero();

        camera = model.getCamera();
        batch = new SpriteBatch();

        debugRenderer = new Box2DDebugRenderer();
        debugShapeRenderer = new ShapeRenderer();

        world = model.getWorld();
    }

    public void render(float deltaTime){
        //Gdx.gl.glClearColor(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        model.getHero().getSprite().draw(batch);
        //batch.draw(hero.getTexture(), hero.getPosition().x,hero.getPosition().y,0,0, hero.getRotation() );
        for (Sprite sprite : model.getBackgroundSpritesToDraw()){
            sprite.draw(batch);
        }
        batch.end();
        debugRenderings();


    }


    private void debugRenderings(){
        debugRenderer.render(world, camera.combined);
        debugShapeRenderer.setProjectionMatrix(camera.combined);
        debugShapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 heroPos = model.getHero().getPosition();
        debugShapeRenderer.point(heroPos.x, heroPos.y, 0);

        Vector3 cameraPos = model.getCamera().position;
        debugShapeRenderer.point(cameraPos.x, cameraPos.y, 0);

        debugShapeRenderer.end();
    }




    @Override
    public void dispose() {
        batch.dispose();

    }
}
