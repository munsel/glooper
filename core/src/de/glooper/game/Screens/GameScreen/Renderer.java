package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.glooper.game.Screens.GameScreen.HelperClasses.CustomOrthographicTiledMapRenderer;
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
    private OrthographicCamera camera, frameCam;
    private SpriteBatch batch;
    private ShapeRenderer debugShapeRenderer;
    private CustomOrthographicTiledMapRenderer tiledMapRenderer;
    private FrameBuffer normalsBuffer;
    private FrameBuffer diffuseBuffer;
    private FrameBuffer resultBuffer;
    private Texture normalsTexture, diffuseTexture;

    private HUD hud;

    private IHero hero;
    ShaderProgram shader;

    //our constants...
    public static final float DEFAULT_LIGHT_Z = 1.3f;
    public static final float AMBIENT_INTENSITY = 0.15f;
    public static final float LIGHT_INTENSITY = 16f;

    public static final Vector3 LIGHT_POS = new Vector3(0f,0f,DEFAULT_LIGHT_Z);

    //Light RGB and intensity (alpha)
    public static final Vector3 LIGHT_COLOR = new Vector3(1f, 0.9f, 0.8f);

    //Ambient RGB and intensity (alpha)
    public static final Vector3 AMBIENT_COLOR = new Vector3(0.6f, 0.7f, 0.8f);

    //Attenuation coefficients for light falloff
    public static final Vector3 FALLOFF = new Vector3(.3f, .3f, 3f);


    public Renderer(WorldModel model ){
        this.model = model;
        this.hero = model.getHero();
        this.hud = model.getHud();


        camera = model.getCamera();
        frameCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //frameCam.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch = new SpriteBatch();

        tiledMapRenderer = new CustomOrthographicTiledMapRenderer(model.getMap(),1f/128f, batch);

        debugRenderer = new Box2DDebugRenderer();
        debugShapeRenderer = new ShapeRenderer();

        world = model.getWorld();

        ShaderProgram.pedantic = false;
        String vert = Gdx.files.internal("shaders/vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/fragment.glsl").readString();
        //shader = new ShaderProgram(VERT, FRAG);
        shader = new ShaderProgram(vert, frag);
        if (!shader.isCompiled())
            throw new GdxRuntimeException("Could not compile shader: "+shader.getLog());
        //print any warnings
        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());

        LIGHT_POS.z = DEFAULT_LIGHT_Z;
        //setup default uniforms
        shader.begin();

        //our normal map
        shader.setUniformi("u_normals", 1); //GL_TEXTURE1

        shader.setUniformf("Resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //light/ambient colors
        //LibGDX doesn't have Vector4 class at the moment, so we pass them individually...
        shader.setUniformf("LightColor", LIGHT_COLOR.x, LIGHT_COLOR.y, LIGHT_COLOR.z, LIGHT_INTENSITY);
        shader.setUniformf("AmbientColor", AMBIENT_COLOR.x, AMBIENT_COLOR.y, AMBIENT_COLOR.z, AMBIENT_INTENSITY);
        shader.setUniformf("Falloff", FALLOFF);

        //LibGDX likes us to end the shader program
        shader.end();

        //batch.setShader(shader);

        normalsBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        diffuseBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        resultBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);


        //camera.zoom = 15;
    }
    float pad = .4f;
    Vector2 relLampPos = new Vector2();
    public void render(float deltaTime){
        batch.setProjectionMatrix(camera.combined);
        //Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float width = camera.viewportWidth * camera.zoom+pad;
        float height = camera.viewportHeight * camera.zoom+pad;
        tiledMapRenderer.setView(camera.combined,
                camera.position.x - width / 2-pad, camera.position.y - height / 2 -pad, width, height);

        normalsBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setShader(null);
        batch.begin();

        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) model.getMap().getLayers().get("normals"));
        //hero.drawNormal(batch);
        batch.end();
        normalsBuffer.end();


        //batch.setShader(shader);
        //model.getClouds().draw(batch);
        //batch.end();

//        model.drawBackground(batch);
        //tiledMapRenderer.setView(camera);

        diffuseBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //tiledMapRenderer.justBind((TiledMapTileLayer) model.getMap().getLayers().get("normals"), 1);
        //tiledMapRenderer.customRenderTileLayer((TiledMapTileLayer)model.getMap().getLayers().get("rock"), 0);
        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) model.getMap().getLayers().get("rock"));
        //tiledMapRenderer.render();

        //batch.begin();

        /*
        	public void draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height,
		float scaleX, float scaleY, float rotation);
         */

        //batch.draw(hero.getTexture(), hero.getPosition().x, hero.getPosition().y, 0,0, hero.getRotation() );


        batch.end();
        diffuseBuffer.end();

        //batch.setProjectionMatrix(frameCam.combined);
        //frameCam.translate(diffuseBuffer.getWidth()/2, diffuseBuffer.getHeight()/2);
        //resultBuffer.begin();
        Gdx.gl.glClearColor(0.05f,0.05f,0.12f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
        batch.setProjectionMatrix(frameCam.combined);
shader.begin();
        relLampPos = hero.getLampPositionRelToView();
        LIGHT_POS.x = relLampPos.x;
        LIGHT_POS.y = relLampPos.y;
        //Gdx.app.log("Renderer", Float.toString(relLampPos.y));
        //LIGHT_POS.x = hero.getLampPosition().x;
        //LIGHT_POS.y = hero.getLampPosition().y;
        shader.setUniformf("LightPos", LIGHT_POS);
shader.end();
        //Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        batch.begin();


        batch.setProjectionMatrix(camera.combined);
        model.getClouds().draw(batch);
        batch.setProjectionMatrix(frameCam.combined);

        batch.setShader(shader);
        normalsTexture = normalsBuffer.getColorBufferTexture();
        //Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        diffuseTexture = diffuseBuffer.getColorBufferTexture();
        //shader.setUniformi("u_texture", 0);
        //Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
        normalsTexture.bind(1);
        diffuseTexture.bind(0);
        //shader.setUniformi("u_normals", 1);

        //batch.draw(normalsTexture,0-frameCam.viewportWidth/2,0-frameCam.viewportHeight/2);
        batch.draw(diffuseTexture, 0 - frameCam.viewportWidth/2,0-frameCam.viewportHeight/2);
        //batch.draw(diffuseTexture, 0 - camera.viewportWidth/2,0-camera.viewportHeight/2);
        batch.setShader(null);
        batch.setProjectionMatrix(camera.combined);
        for (IEntity entity: model.getEntities()){
            entity.render(batch);
        }
        hero.draw(batch);
        batch.end();


        //resultBuffer.end();


/*        batch.setProjectionMatrix(camera.combined);

        //Gdx.gl.glClearColor(0.08f, 0.12f, 0.28f,1);
        Gdx.gl.glClearColor(1f, 1f, 1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.draw(diffuseTexture,
        //0,0, diffuseTexture.getWidth()/128, diffuseTexture.getHeight()/128);
        batch.setShader(null);
        batch.begin();
        batch.draw(resultBuffer.getColorBufferTexture(),
                camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2,
                diffuseTexture.getWidth() / 128,
                diffuseTexture.getHeight() / 128);
        batch.end();
*/
        hud.draw();
        //debugRenderings();
        //camera.zoom = 15;
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
