package de.glooper.game.Screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import de.glooper.game.GlooperMainClass;
import de.glooper.game.Screens.GameScreen.HelperClasses.GameStage;

/**
 * Created by munsel on 06.06.15.
 */
public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getSimpleName();

    /**
     * Controller View Model
     */
    private Controller controller;
    private Renderer renderer;
    private WorldModel model;


    private GameStage gameStage;
    private ResourceManager rm;

    public GameScreen(GlooperMainClass mainClass){
        super();
        //rm = new ResourceManager();
        //rm.initAllResources();
        //gameStage = new GameStage(rm);
        model = new WorldModel();
        controller = new Controller(model);
        renderer = new Renderer(model);
        Gdx.input.setInputProcessor(controller);
        //Gdx.input.setInputProcessor(gameStage);

    }

    public void show() {

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        model.update(delta);
        renderer.render(delta);


        //gameStage.act();
        //gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
