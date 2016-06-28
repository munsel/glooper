package de.glooper.game.model.Heros;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;
import de.glooper.game.Screens.GameScreen.WorldModel;


/**
 * Created by munsel on 15.06.15.
 */
public class Glooper extends Hero {

    public static final String HeroName = "glooper";

    public Glooper(World world, OrthographicCamera camera, WorldModel model) {

        super(world, camera, HeroName, model);
    }
}







