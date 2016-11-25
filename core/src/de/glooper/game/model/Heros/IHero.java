package de.glooper.game.model.Heros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by munsel on 27.07.15.
 */
public interface IHero {
    void draw(SpriteBatch batch);
    void die();
    void update(float delta);
    void touchDownAction();
    void touchUpAction();
    Vector2 getPosition();
    Vector2 getLampPosition();
    Vector2 getLampPositionRelToView();
    void drawNormal(SpriteBatch batch);
    float getRotation();
    Vector2 getCameraFixpoint();
}
