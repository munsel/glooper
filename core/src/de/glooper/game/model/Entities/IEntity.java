package de.glooper.game.model.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by munsel on 27.06.16.
 */
public interface IEntity {
    void reset();
    void setUsed();
    boolean isUsed();

    void update(float delta);
    void render(SpriteBatch batch);
    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
    void setXFlip(boolean flip);
    void setYFlip(boolean flip);
    void flipX();
    boolean getXFlip();
    boolean getYFlip();
    String getName();
    Body getBody();
    Sprite getSprite();
    void removeItself();
    void removeBody();
    void createBody();
    void setBehaviour(IEntityBehaviour behaviour);
}
