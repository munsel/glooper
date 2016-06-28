package de.glooper.game.model.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.glooper.game.model.Tile.IWorldTile;


/**
 * Created by munsel on 27.06.16.
 */
public class Entity implements IEntity {
    private final String TAG = Entity.class.getName();


    private String name;
    private float x, y;
    private float width, height;
    private float stateTime;
    private Animation animation;
    private IEntityBehaviour behaviour;

    private World world;
    private Body body;

    public Entity(World world, TextureAtlas atlas, String name, float x, float y){
        this.x = x;
        this.y = y;
        this.name = name;
        this.world = world;
        animation = new Animation(.15f, atlas.getRegions());
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    @Override
    public void update(float delta) {
        stateTime+= delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(animation.getKeyFrame(stateTime), x, y, width, height);
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
    public String getName() {
        return name;
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
