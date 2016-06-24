package de.glooper.game.model.Background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by munsel on 16.06.16.
 */
public interface IBackground {
    void draw(SpriteBatch batch);
    void update(float delta);
}
