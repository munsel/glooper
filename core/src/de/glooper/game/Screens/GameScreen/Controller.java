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
    public boolean keyTyped(char character) {
        if (character == Input.Keys.W)
            model.setGlooperWobblieness(
                    model.getGlooperWobblienessFreqency()+stepSize,
                    model.getGlooperWobblienessAmplitude());

        if (character == Input.Keys.S){
            model.setGlooperWobblieness(
                    model.getGlooperWobblienessFreqency()-stepSize,
                    model.getGlooperWobblienessAmplitude()
            );
        }
        if (character == Input.Keys.D){
            model.setGlooperWobblieness(
                    model.getGlooperWobblienessFreqency(),
                    model.getGlooperWobblienessAmplitude()+stepSize
            );
        }
        if (character == Input.Keys.A){
            model.setGlooperWobblieness(
                    model.getGlooperWobblienessFreqency(),
                    model.getGlooperWobblienessAmplitude()-stepSize
            );
            Gdx.app.debug(TAG, "pressed left");
        }



        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        model.glooperLeft();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        model.glooperRight();
        return true;
    }
}
