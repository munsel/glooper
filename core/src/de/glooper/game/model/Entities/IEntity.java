package de.glooper.game.model.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by munsel on 27.06.16.
 */
public interface IEntity {
    void update(float delta);
    void render(SpriteBatch batch);
    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
    String getName();
    void setBehaviour(IEntityBehaviour behaviour);
}
