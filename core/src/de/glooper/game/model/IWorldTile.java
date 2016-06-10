package de.glooper.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vincent on 18.07.15.
 */
public interface IWorldTile {
    void draw(SpriteBatch batch);
    String getName();
    void update(float delta);
    void attachSensor(TileBorderSensor sensor);
    void drawDrebugSensors(ShapeRenderer shapeRenderer);
}
