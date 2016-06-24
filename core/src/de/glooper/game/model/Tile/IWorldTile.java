package de.glooper.game.model.Tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.glooper.game.Screens.GameScreen.Heros.Hero;
import de.glooper.game.model.Tile.BorderSensor.TileBorderSensor;

/**
 * Created by vincent on 18.07.15.
 */
public interface IWorldTile {
    String getName();
    String getNameOfSet();
    float getX();
    float getY();

    void update(float delta);
    void draw(SpriteBatch batch);
    void attachSensor(TileBorderSensor sensor);
    boolean isHeroInside();
    void drawDrebugSensors(ShapeRenderer shapeRenderer);
    void removeBody();
}
