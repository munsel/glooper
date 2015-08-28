package de.glooper.game.Screens.GameScreen.Heros;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;


/**
 * Created by munsel on 15.06.15.
 */
public class Glooper implements IHero, Disposable {

    private static final String TAG = Glooper.class.getSimpleName();

    /**
     * constants
     */
    private static final float density = 1;
    private static final float friction = 1;
    private static final float restitution = 0;
    private static final float radius = 0.24f;

    /**
     * variables
     */
    private float timeAlive;
    private float absoluteOmega;
    private float velocity;
    private float angularVelocity;
    private float currentAngularVelocity;

    private  boolean wiggling;

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

    private float wiggleAmplitude;
    private float wiggleFrequency;

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
    private final int RAYS_NUM = 500;
    private float lightDistance = 5;

    /**
     * Sprite stuff
     */
    private Sprite sprite;
    private Animation animation;

    private OrthographicCamera camera;

    public Glooper(World world, OrthographicCamera camera) {
        this.world = world;
        this.camera = camera;

        timeAlive = 0;
        absoluteOmega = 2;
        velocity = 2;
        angularVelocity = 2;
        currentAngularVelocity = angularVelocity;


        wiggleAmplitude = 0.3f;
        wiggleFrequency = 14;
        wiggling = false;

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        FixtureDef fdef = new FixtureDef();
        fdef.density = density;
        fdef.friction = friction;
        fdef.restitution = restitution;
        fdef.shape = new CircleShape();
        fdef.shape.setRadius(radius);
        body = this.world.createBody(bdef);
        body.createFixture(fdef);
        body.setAngularVelocity(angularVelocity);

        rayHandler = new RayHandler(world);
        lamp = new PointLight(rayHandler, RAYS_NUM,
                new Color(0.8f,0.8f,0.4f,1),lightDistance,
                0,0
                );

        Texture texture = new Texture("glooper.png");
        sprite = new Sprite(texture);
        sprite.setSize(0.85f, 0.48f);
        sprite.setOrigin(0.61f, 0.24f);
        //sprite.setCenter(0.61f,0.24f);

    }


    public float getAbsoluteOmega(){return absoluteOmega;}
    public void setAngularVelocity(float newOmega){
        angularVelocity = newOmega;
        body.setAngularVelocity(angularVelocity);
    }

    public Sprite getSprite(){return sprite;}


    @Override
    public void die() {

    }

    @Override
    public void update(float deltaTime) {
        //update body data
        //movement of the body
        timeAlive += 1f/60f;//deltaTime;
        if (timeAlive > 15) wiggling=true;
        float angle = body.getAngle();
        float x = MathUtils.cos(angle);
        float y = MathUtils.sin(angle);
        Vector2 wiggleOffset = new Vector2(0,0);
        if (wiggling){
            float xWiggle = MathUtils.cos(angle + MathUtils.PI/2.f);
            float yWiggle = MathUtils.sin(angle + MathUtils.PI / 2.f);
            float offset = wiggleAmplitude*MathUtils.sin(wiggleFrequency * timeAlive);
            wiggleOffset.set(offset*xWiggle, offset* yWiggle);
        }
        Vector2 velocity2D = new Vector2(
                MathUtils.cos(angle)*velocity,
                MathUtils.sin(angle)*velocity);
        body.setLinearVelocity(velocity2D.add(wiggleOffset));

        //align sprite position according to body
        float posX =body.getPosition().x-(2*radius);
        float posY = body.getPosition().y-(radius);
        sprite.setPosition(posX, posY);
        sprite.setRotation(angle * MathUtils.radiansToDegrees);

        lamp.setPosition(body.getPosition().x-MathUtils.cos(body.getAngle()+0.6f)*0.3f,
                body.getPosition().y-MathUtils.sin(body.getAngle()+9.6f)*0.3f);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();

    }

    @Override
    public void touchDownAction() {
        currentAngularVelocity = -angularVelocity;
        body.setAngularVelocity(currentAngularVelocity);

    }

    @Override
    public void touchUpAction() {
        currentAngularVelocity = angularVelocity;
        body.setAngularVelocity(currentAngularVelocity);

    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();/* new Vector2(sprite.getX(), sprite.getY()*/
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public float getRotation() {
        return body.getTransform().getRotation();
    }

    @Override
    public void dispose() {
        rayHandler.dispose();
    }
}







