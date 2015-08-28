package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by munsel on 06.06.15.
 */
public class Controller extends InputAdapter{
    private static final String TAG = Controller.class.getSimpleName();

    private WorldModel model;
    private static float stepSize=1f;

    public Controller(WorldModel model){
        super();
        this.model = model;
    }



    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        model.getHero().touchDownAction();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        model.getHero().touchUpAction();
        return true;
    }
}
