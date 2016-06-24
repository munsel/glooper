package de.glooper.game.Screens.GameScreen.Heros;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Box2DUtils;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.SaveStateManagement.Safeable;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.Screens.GameScreen.HelperClasses.HUD;
import de.glooper.game.Screens.GameScreen.WorldModel;
import de.glooper.game.model.Tile.WorldTile;
import net.dermetfan.gdx.physics.box2d.*;

/**
 * Created by munsel on 30.08.15.
 */
public class Hero implements IHero, Safeable, Disposable {
    private static final String TAG = Hero.class.getSimpleName();

/**
 * constants
 */
private static final float SIZE_X = 1;
        private static final float SIZE_Y = 0.61f;
    private static final float density = 1;
private static final float friction = 1;
private static final float restitution = 0;
private static final float radius = 0.24f;

/**
 * variables
 */
    private float stamina =1f;
    private int frames;
    private float absoluteOmega;
    private float velocity;
    private float angularVelocity;
    private float currentAngularVelocity;
    private  boolean wiggling;
    private float wiggleAmplitude;
    private float wiggleFrequency;

    /**
     *  model reference
     */
    private WorldModel model;

/**
 * box2D stuff
 */

private World world;
private Body body;

/**
 * box2D lights stuff
 */
private RayHandler rayHandler;
private PointLight lamp;
    private final float BODY_TO_LAMP_RADIUS =0.39f;
    private float bodyToLampStartingAngle=0.49f;
private final int RAYS_NUM = 500;
private float lightDistance = 4;

/**
 * Sprite stuff
 */
private Sprite sprite;
    private final float offsetSpriteX = 0.6f;
    private final float offsetSpriteY = 0.24f;
private Animation animation;

private OrthographicCamera camera;

public Hero(World world, OrthographicCamera camera, String heroName, WorldModel model) {
        this.world = world;
        this.camera = camera;
    this.model = model;

        frames = 0;
        absoluteOmega = 2;
        velocity = 2;
        angularVelocity = 2;
        currentAngularVelocity = angularVelocity;


        wiggleAmplitude = 0.3f;
        wiggleFrequency = 14;
        wiggling = false;


        //BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("Heros/"+heroName+".json"));

        BodyDef bdef = new BodyDef();
        bdef.position.set(WorldTile.TILE_SIZE/2,WorldTile.TILE_SIZE/2);
        //bdef.type = BodyDef.BodyType.StaticBody;
        bdef.type = BodyDef.BodyType.DynamicBody;

        FixtureDef fdef = new FixtureDef();
        fdef.density = density;
        fdef.friction = friction;
        fdef.restitution = restitution;
        fdef.filter.categoryBits = 0x01;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fdef.shape = shape;

        body = this.world.createBody(bdef);
        body.createFixture(fdef);
        shape.dispose();
        //loader.attachFixture(body, heroName, fdef,SIZE_X);

        body.setAngularVelocity(angularVelocity);
    world.setContactListener(new ContactListener() {
        @Override
        public void beginContact(Contact contact) {
            if(contact.getFixtureA().getFilterData().categoryBits == 0x01)die();
            if(contact.getFixtureB().getFilterData().categoryBits == 0x01)die();
        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    });

        rayHandler = new RayHandler(world);
        lamp = new PointLight(rayHandler, RAYS_NUM,
                new Color(0.33f,0.86f,1f,1),
                lightDistance, 0,0);
        lamp.setSoftnessLength(0.5f);

        Texture texture = new Texture(heroName+".png");
        sprite = new Sprite(texture);
        sprite.setSize(SIZE_X, SIZE_Y);
        sprite.setOrigin(offsetSpriteX, offsetSpriteY);

        //sprite.setCenter(0.61f,0.24f);

        }





    public float getStamina(){return stamina;}
    public float getWiggleFrequency() {
        return wiggleFrequency;
    }
    public void setWiggleFrequency(float wiggleFrequency) {
        this.wiggleFrequency = wiggleFrequency;
    }
    public float getWiggleAmplitude() {
        return wiggleAmplitude;
    }
    public void setWiggleAmplitude(float wiggleAmplitude) {
        this.wiggleAmplitude = wiggleAmplitude;
    }
    public boolean isWiggling() {
        return wiggling;
    }
    public void setWiggling(boolean wiggling) {
        this.wiggling = wiggling;
    }
    public float getAbsoluteOmega(){return absoluteOmega;}
    public void setAngularVelocity(float newOmega){
        angularVelocity = newOmega;
        body.setAngularVelocity(angularVelocity);
    }

    public Sprite getSprite(){return sprite;}


    public void init(SaveState saveState){
        if (model.isGameOver()){
            wiggling = true;
            stamina = 1;
            body.setTransform(WorldTile.TILE_SIZE / 2, WorldTile.TILE_SIZE / 2, 0);
        } else{
            load(saveState);
        }
        alignSpriteToBody();
    }

    public void die() {
        model.setGameOver();
        Gdx.app.log(TAG, "i am dead now!");
    }

    public void update(float deltaTime) {
        //update body data
        //movement of the body
        frames++;
        float angle = body.getAngle();
        Vector2 wiggleOffset = new Vector2(0,0);
        if (wiggling){
            float xWiggle = MathUtils.cos(angle + MathUtils.PI/2.f);
            float yWiggle = MathUtils.sin(angle + MathUtils.PI / 2.f);
            float offset = wiggleAmplitude*MathUtils.sin(wiggleFrequency * frames/60f);
            wiggleOffset.set(offset*xWiggle, offset* yWiggle);
        }
        Vector2 velocity2D = new Vector2(
        MathUtils.cos(angle)*velocity,
        MathUtils.sin(angle)*velocity);
        body.setLinearVelocity(velocity2D.add(wiggleOffset));

        alignSpriteToBody();

        lamp.setPosition(body.getPosition().x + MathUtils.cos(body.getAngle() + bodyToLampStartingAngle) * BODY_TO_LAMP_RADIUS,
            body.getPosition().y + MathUtils.sin(body.getAngle() + bodyToLampStartingAngle) * BODY_TO_LAMP_RADIUS);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();

        stamina -= deltaTime * 0.03;
        if( stamina <= 0){
            die();
        }
    }

    private void alignSpriteToBody(){
        float posX =body.getPosition().x- offsetSpriteX;
        float posY = body.getPosition().y- offsetSpriteY;
        sprite.setPosition(posX, posY);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public void touchDownAction() {
        currentAngularVelocity = -angularVelocity;
        body.setAngularVelocity(currentAngularVelocity);

    }

    public void touchUpAction() {
        currentAngularVelocity = angularVelocity;
        body.setAngularVelocity(currentAngularVelocity);
    }

    public Vector2 getPosition() {
        return body.getPosition();/* new Vector2(sprite.getX(), sprite.getY()*/
    }

    public Vector2 getLampPosition() {
        return lamp.getPosition();
    }

    public Texture getTexture() {
        return null;
    }

    public float getRotation() {
        return body.getTransform().getRotation();
    }

    public void dispose() {
        rayHandler.dispose();
    }

    @Override
    public void save(SaveState saveState) {
        saveState.setHeroX(body.getPosition().x);
        saveState.setHeroY(body.getPosition().y);
        saveState.setHeroRot(body.getAngle());
        saveState.setHeroStamina(stamina);
    }

    @Override
    public void load(SaveState saveState) {
        body.setTransform(saveState.getHeroX(), saveState.getHeroY(), saveState.getHeroRot());
        stamina=saveState.getHeroStamina();
        alignSpriteToBody();
    }
}








