package de.glooper.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vincent on 18.07.15.
 */
public interface IDynamicWorld {
    void init();
    void createNewTile( float x, float y, WorldTile.DIRECTION direction);
    void setCurrentTile(IWorldTile newCurrentTile);
    Vector2 getCurrentPos();
    String getCurrentName();
    void update(float delta);
    void draw(SpriteBatch batch);
    void drawDebugSensors(ShapeRenderer shapeRenderer);
}
