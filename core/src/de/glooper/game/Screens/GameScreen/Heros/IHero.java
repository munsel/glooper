package de.glooper.game.Screens.GameScreen.Heros;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by munsel on 27.07.15.
 */
public interface IHero {
    void update(float delta);
    void touchDownAction();
    void touchUpAction();
    Vector2 getPosition();
}
