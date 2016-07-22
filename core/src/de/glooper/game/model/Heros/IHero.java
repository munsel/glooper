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
    void drawLamp(SpriteBatch batch);
    void die();
    void update(float delta);
    void touchDownAction();
    void touchUpAction();
    Vector2 getPosition();
    Vector2 getLampPosition();
    float getRotation();

}
