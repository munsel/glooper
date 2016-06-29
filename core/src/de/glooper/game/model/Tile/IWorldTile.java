package de.glooper.game.model.Tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.SaveStateManagement.SaveState;
import de.glooper.game.model.Entities.IEntity;
import de.glooper.game.model.Tile.BorderSensor.TileBorderSensor;

/**
 * Created by vincent on 18.07.15.
 */
public interface IWorldTile {
    String getName();
    String getNameOfSet();
    float getX();
    float getY();
    void init(SaveState saveState);

    void update(float delta);
    void draw(SpriteBatch batch);
    void attachSensor(TileBorderSensor sensor);
    boolean isHeroInside();
    Array<IEntity> getEntities();
    void removeEntity(IEntity entity);
    void drawDebugSensors(ShapeRenderer shapeRenderer);
    void removeBody();
}
