package de.glooper.game.model.Heros;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.SaveStateManagement.Safeable;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.Screens.GameScreen.WorldModel;
import de.glooper.game.model.Entities.Entity;
import de.glooper.game.model.Entities.IEntity;
import de.glooper.game.model.Physics.Box2DCategoryBits;
import de.glooper.game.model.Tile.WorldTile;

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
        private Sprite lampSprite;
        private final float BODY_TO_LAMP_RADIUS =0.39f;
        private float bodyToLampStartingAngle=0.49f;
        private final int RAYS_NUM = 500;
        private float lightDistance = 7f;
        private float lightAlpha = 1f;

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
        fdef.filter.categoryBits = Box2DCategoryBits.HERO;
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
            if(contact.getFixtureA().getFilterData().categoryBits == Box2DCategoryBits.HERO)
                contactAction(contact.getFixtureB());
            if(contact.getFixtureB().getFilterData().categoryBits == Box2DCategoryBits.HERO)
                contactAction(contact.getFixtureA());
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

    Texture lampTexture = new Texture("lamp.png");
    lampTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    lampSprite = new Sprite(lampTexture);
    lampSprite.setSize(2*lightDistance, 2*lightDistance);



        Texture texture = new Texture(heroName+".png");
        sprite = new Sprite(texture);
        sprite.setSize(SIZE_X, SIZE_Y);
        sprite.setOrigin(offsetSpriteX, offsetSpriteY);

        //sprite.setCenter(0.61f,0.24f);

        }





    private void contactAction(Fixture fixture){
        Gdx.app.log(TAG, "ima contact action");
        if (fixture.getFilterData().categoryBits == Box2DCategoryBits.FOOD){
            Gdx.app.log(TAG, "hero's got some food YA");
            stamina += 0.4;
            if (stamina>1) stamina =1;
            model.addToScore(1);
            ((Entity.EntityBodyData)fixture.getBody().getUserData()).needsToBeRemoved = true;
            //((IEntity)fixture.getBody().getUserData()).removeItself();
        }
        if (fixture.getFilterData().categoryBits == Box2DCategoryBits.ROCK){
            Gdx.app.log(TAG, "hero's got some ROCK YA");
            die();
        }
        if (fixture.getFilterData().categoryBits == Box2DCategoryBits.ENEMY){
            Gdx.app.log(TAG, "hero's got some ENEMY YA");
            die();
        }
        if (fixture.getFilterData().categoryBits == Box2DCategoryBits.HERO){
            Gdx.app.log(TAG, "ima hero NOO");
        }
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

    @Override
    public void draw(SpriteBatch batch) {
        //rayHandler.render();
        sprite.draw(batch);
    }

    @Override
    public void drawLamp(SpriteBatch batch) {
        lampSprite.draw(batch,lightAlpha);
    }


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
        Vector2 lampPos = getLampPosition();
        lampSprite.setPosition(lampPos.x, lampPos.y);


        lamp.setPosition(lampPos.x, lampPos.y);
        rayHandler.setCombinedMatrix(camera.combined);
        //rayHandler.updateAndRender();
        rayHandler.update();
        //rayHandler.render();


        stamina -= deltaTime * 0.03;
        if( stamina <= 0){
            die();
        }
    }

    public Vector2 getLampPosition(){
        Vector2 lampPos = new Vector2();
        lampPos.x =body.getPosition().x + MathUtils.cos(body.getAngle() + bodyToLampStartingAngle) * BODY_TO_LAMP_RADIUS;
        lampPos.x -= lampSprite.getWidth()/2;
        lampPos.y =body.getPosition().y + MathUtils.sin(body.getAngle() + bodyToLampStartingAngle) * BODY_TO_LAMP_RADIUS;
        lampPos.y -= lampSprite.getHeight()/2;
        return lampPos;
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

    public float getRotation() {
        return body.getTransform().getRotation();
    }

    public void dispose() {
        //rayHandler.dispose();
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








