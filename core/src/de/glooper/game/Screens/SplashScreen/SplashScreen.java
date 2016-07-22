package de.glooper.game.Screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.glooper.game.GlooperMainClass;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.Screens.GameScreen.HelperClasses.CameraHelper;

import java.security.SecureRandom;


/**
 * Created by munsel on 30.06.16.
 */
public class SplashScreen implements Screen {
    private final String TAG = SplashScreen.class.getSimpleName();

    private GlooperMainClass game;

    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private Animation animation;
    private float aniX;
    private final float PAD_LR = 50;
    private final float POS_Y = 100;

    private Vector2 setPos, currentPos;

    float maxProgressWidth;


    public SplashScreen( GlooperMainClass mainClass, AssetManager assetManager){
        game = mainClass;
        this.assetManager = assetManager;
        spriteBatch = new SpriteBatch();
        TextureAtlas atlas = assetManager.get("Heros/glooper.pack");
        animation = new Animation(0.3f, atlas.getRegions(), Animation.PlayMode.LOOP);
        setPos = new Vector2();
        currentPos = new Vector2(PAD_LR, POS_Y);
    }



    @Override
    public void show() {
        maxProgressWidth = Gdx.graphics.getWidth()
                -animation.getKeyFrame(0).getRegionWidth()-2*PAD_LR;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if(assetManager.update()){
            game.createAssetsInstance();
            game.backToMenu();
        }
        float progress = assetManager.getProgress();
        aniX = progress*maxProgressWidth+PAD_LR;
        setPos.set(aniX, POS_Y);
        currentPos.lerp(setPos, 0.1f);
        //currentPos = currentPos.add(CameraHelper.lerp(setPos,currentPos,delta ));

        spriteBatch.begin();
        spriteBatch.draw(animation.getKeyFrame(progress),
                currentPos.x, currentPos.y);
        spriteBatch.end();
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
        spriteBatch.dispose();
    }
}
