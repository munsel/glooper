package de.glooper.game.Screens.GameScreen.Heros;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;


/**
 * Created by munsel on 15.06.15.
 */
public class Glooper extends Hero {

    public static final String HeroName = "glooper";

    public Glooper(World world, OrthographicCamera camera) {
        super(world, camera, HeroName);
    }
}







