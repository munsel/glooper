package de.glooper.game.Screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.glooper.game.GlooperMainClass;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;

/**
 * Created by munsel on 06.06.15.
 */
public class MenuScreen implements Screen {
    private static final String TAG = MenuScreen.class.getSimpleName();

    private GlooperMainClass game;
    private Stage stage;

    public MenuScreen(final GlooperMainClass game){
        this.game = game;
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        Skin skin = AssetHandler.instance.skin;

        TextButton startButton = new TextButton("start", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.startGame();
            }
        });

        TextButton aboutButton = new TextButton("about", skin);
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton quitButton = new TextButton("quit", skin);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        CheckBox soundsCheckBox = new CheckBox("sound",skin);
        CheckBox musicOnCheckBox = new CheckBox("music", skin);

        table.add(startButton).left().fillX().expandX();
        table.add(soundsCheckBox).right();
        table.row();
        table.add(aboutButton).left().expandX();
        table.add(musicOnCheckBox).right();
        table.row();
        table.add(quitButton).center();


        stage.addActor(table);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}
