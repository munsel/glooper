package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import de.glooper.game.GlooperMainClass;
import de.glooper.game.SaveStateManagement.SaveStateManager;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.Screens.GameScreen.HelperClasses.HUD;
import de.glooper.game.Screens.GameScreen.HelperClasses.HeroStatusDrawer;

/**
 * Created by munsel on 06.06.15.
 */
public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getSimpleName();

    private boolean paused;

    /**
     * Controller View Model
     */
    private Controller controller;
    private Renderer renderer;
    private WorldModel model;

    private InputMultiplexer multiplexer;

    private GlooperMainClass mainClass;


    public GameScreen(GlooperMainClass mainClass){
        this.mainClass = mainClass;
        model = new WorldModel(this);
        controller = new Controller(model);
        renderer = new Renderer(model);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(model.getHud());
        multiplexer.addProcessor(controller);

    }

    public void show() {
            model.init();
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void backToMenu(){
        mainClass.backToMenu();
    }


    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClearColor(0.08f, 0.12f, 0.28f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        model.update(delta);
        renderer.render(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "paused");
        paused = true;
        model.save(model.getSaveState());
        SaveStateManager.saveSaveState(model.getSaveState());
        multiplexer.removeProcessor(controller);
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resumed");
        paused = false;
        multiplexer.addProcessor(controller);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        model.dispose();
        controller = null;
        renderer.dispose();


    }

    public boolean isPaused() {
        return paused;
    }
}
