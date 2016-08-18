package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.HelperClasses.HUD;
import de.glooper.game.Screens.GameScreen.HelperClasses.StomachStatusDrawer;
import de.glooper.game.model.Entities.IEntity;
import de.glooper.game.model.Heros.IHero;

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
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private HUD hud;
    private StomachStatusDrawer statusDrawer;

    private IHero hero;

    public Renderer(WorldModel model ){
        this.model = model;
        this.hero = model.getHero();

        this.hud = model.getHud();
        //this.statusDrawer = model.getStatusDrawer();

        camera = model.getCamera();
        batch = new SpriteBatch();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(model.getMap(),1f/128f, batch);



        debugRenderer = new Box2DDebugRenderer();
        debugShapeRenderer = new ShapeRenderer();

        world = model.getWorld();
        //camera.zoom = 15;
    }
    float pad = .7f;
    public void render(float deltaTime){

        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        model.getClouds().draw(batch);
        batch.end();

//        model.drawBackground(batch);
        //tiledMapRenderer.setView(camera);

        float width = camera.viewportWidth * camera.zoom+pad;
        float height = camera.viewportHeight * camera.zoom+pad;
        tiledMapRenderer.setView(camera.combined,
                camera.position.x - width / 2-pad, camera.position.y - height / 2 -pad, width, height);

        tiledMapRenderer.render();

        batch.begin();
        for (IEntity entity: model.getEntities()){
            entity.render(batch);
        }

        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);

        hero.drawLamp(batch);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        hero.draw(batch);

        /*
        	public void draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height,
		float scaleX, float scaleY, float rotation);
         */

        //batch.draw(hero.getTexture(), hero.getPosition().x, hero.getPosition().y, 0,0, hero.getRotation() );

        //statusDrawer.draw(batch);
        batch.end();
        hud.draw();
        //debugRenderings();
    }


    private void debugRenderings(){
        debugRenderer.render(world, camera.combined);
        debugShapeRenderer.setProjectionMatrix(camera.combined);
        debugShapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 heroPos = model.getHero().getPosition();
        debugShapeRenderer.setColor(0, 1, 0, 1);
        debugShapeRenderer.point(heroPos.x, heroPos.y, 0);

        Vector3 cameraPos = model.getCamera().position;
        debugShapeRenderer.setColor(1,0,0,1);
        debugShapeRenderer.point(cameraPos.x, cameraPos.y, 0);
/*
        Vector2 heroSpritePos = new Vector2( model.getHero().getSprite().getX(),model.getHero().getSprite().getY());
        debugShapeRenderer.setColor(0,0,1,1);
        debugShapeRenderer.point(heroSpritePos.x, heroSpritePos.y, 0);

        Vector2 lampPos = model.getHero().getLampPosition();
        debugShapeRenderer.setColor(0, 1, 1, 1);
        debugShapeRenderer.point(lampPos.x, lampPos.y, 0);

        Vector2 rotationPoint =  new Vector2( model.getHero().getSprite().getOriginX(),model.getHero().getSprite().getOriginY());
        debugShapeRenderer.setColor(1,0 , 1, 1);
        rotationPoint.add(heroSpritePos);
        debugShapeRenderer.point(rotationPoint.x, rotationPoint.y, 0);
*/
        debugShapeRenderer.end();

        debugShapeRenderer.setColor(0,0,1,1);
        debugShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        model.debugDrawSensors(debugShapeRenderer);

        debugShapeRenderer.end();
    }




    @Override
    public void dispose() {
        batch.dispose();

    }
}
