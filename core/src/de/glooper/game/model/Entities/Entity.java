package de.glooper.game.model.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.model.Tile.IWorldTile;
import de.glooper.game.model.Tile.WorldTile;


/**
 * Created by munsel on 27.06.16.
 */
public class Entity implements IEntity {
    private final String TAG = Entity.class.getName();



    public class EntityBodyData {
        public boolean needsToBeRemoved;
        public EntityBodyData(){needsToBeRemoved = false;}
    }


    private String name;
    private float x, y;
    private boolean flipX, flipY;
    private boolean needsToBeRendered;
    private float width, height;
    private float stateTime;
    private short categoryBits;
    private Animation animation;
    private IEntityBehaviour behaviour;

    private World world;
    private Body body;

    Array<IEntity> container;


    public Entity(Array<IEntity> container,World world, TextureAtlas atlas, String name,
                  float x, float y,
                  float width, float height,
                  short categoryBits){
        this.container = container;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.world = world;
        this.categoryBits = categoryBits;
        animation = new Animation(.15f, atlas.getRegions());
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        //reset();
    }

    @Override
    public void reset() {
        needsToBeRendered = true;
        stateTime = 0;
        createBody();
    }

    @Override
    public void setUsed() {
        needsToBeRendered = false;
        removeBody();
    }

    @Override
    public void update(float delta) {
            stateTime+= delta;
        if (needsToBeRendered) {
            behaviour.update(delta);
            body.setTransform(x + width * .5f, y + height * .5f, 0);
            if ((!animation.getKeyFrame(stateTime).isFlipX() && flipX)
                    || (animation.getKeyFrame(stateTime).isFlipX() && !flipX))
                animation.getKeyFrame(stateTime).flip(true, false);

            if (((EntityBodyData) body.getUserData()).needsToBeRemoved) {
                setUsed();
                stateTime = 0;
            }

        }else if (stateTime>10)reset();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (needsToBeRendered)
            batch.draw(animation.getKeyFrame(stateTime)
                , x, y, width, height);
    }

    @Override
    public float getX() {return x;}

    @Override
    public float getY() {return y;}

    @Override
    public void setX(float x) {this.x = x;}

    @Override
    public void setY(float y) {this.y = y;}

    @Override
    public void setXFlip(boolean flip) {
        flipX = flip;
    }

    @Override
    public void setYFlip(boolean flip) {
        flipY = flip;
    }

    @Override
    public void flipX() {
    }

    @Override
    public boolean getXFlip() {
        return flipX;
    }

    @Override
    public boolean getYFlip() {
        return flipY;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void removeItself() {
        removeBody();
        container.removeValue(this,false);
    }

    @Override
    public void removeBody() {
        world.destroyBody(body);
        body = null;
    }

    @Override
    public void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x+width*.5f, y+height*.5f);
        //bdef.type = BodyDef.BodyType.StaticBody;
        bdef.type = BodyDef.BodyType.DynamicBody;
        FixtureDef fdef = new FixtureDef();
        fdef.density = 1;
        fdef.friction = 1;
        fdef.restitution = 0;
        fdef.isSensor = true;
        fdef.filter.categoryBits = categoryBits;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width*.5f, height*.5f);

        fdef.shape = shape;

        body = this.world.createBody(bdef);
        body.createFixture(fdef);
        //body.setUserData(this);
        body.setUserData(new EntityBodyData());

        shape.dispose();
    }

    @Override
    public void setBehaviour(IEntityBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
