package de.glooper.game.Screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private Image headerImage, menubgImage;

    private  final float V = Gdx.graphics.getHeight();
    private  final float H = Gdx.graphics.getWidth();

    public MenuScreen(final GlooperMainClass game){
        this.game = game;
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        Skin skin = AssetHandler.instance.skin;


        headerImage = new Image(skin, "glooper_title");
        headerImage.setPosition((H-headerImage.getWidth())/2, V*.55f);
        menubgImage = new Image(skin, "menubg");
        menubgImage.setPosition((H-menubgImage.getWidth())/2,V*.06f);

        stage.addActor(menubgImage);
        stage.addActor(headerImage);
        headerImage.addAction(alpha(0));
        menubgImage.addAction(alpha(0));


        Button startButton = new Button(skin, "startbutton");
        startButton.setPosition((H-startButton.getWidth())/2, V*.45f);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.startGame();
            }
        });

        /*Button aboutButton = new Button("about", skin);
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });*/
        stage.addActor(startButton);

        Button scoresButton = new Button(skin, "scoresbutton");
        scoresButton.setPosition((H-scoresButton.getWidth())/2, V*.31f);
        scoresButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                return;
            }
        });
        stage.addActor(scoresButton);

        Button quitButton = new Button(skin, "quitbutton");
        quitButton.setPosition((H-quitButton.getWidth())/2, V*.17f);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        stage.addActor(quitButton);

        CheckBox soundsCheckBox = new CheckBox("sound",skin);
        CheckBox musicOnCheckBox = new CheckBox("music", skin);


    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        headerImage.addAction(sequence(alpha(1, .05f), run(new Runnable() {
            @Override
            public void run() {
                menubgImage.addAction(alpha(1, 0.5f));
            }
        })));

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
        headerImage.addAction(alpha(0));
        menubgImage.addAction(alpha(0));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
