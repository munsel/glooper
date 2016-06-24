package de.glooper.game.Screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.glooper.game.GlooperMainClass;
import de.glooper.game.Screens.MenuScreen.Animations.GlooperEyes;

/**
 * Created by munsel on 06.06.15.
 */
public class MenuScreen implements Screen {

    private static final String TAG = MenuScreen.class.getSimpleName();


    private GlooperEyes glooperEyes;
    private SpriteBatch batch;

    private GlooperMainClass game;
    private InputProcessor processor;

    public MenuScreen(final GlooperMainClass game){
        this.game = game;
        glooperEyes = new GlooperEyes();
        batch = new SpriteBatch();

        processor = new InputAdapter() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                game.startGame();
                return true;
            }
        };


    }

    public void show() {
        Gdx.input.setInputProcessor(processor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        glooperEyes.update(delta);

        batch.begin();
        batch.draw(glooperEyes.getCurrentTexture(), 30, 100);
        batch.end();



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
