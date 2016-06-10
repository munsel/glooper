package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.glooper.game.Screens.GameScreen.GameScreen;

/**
 * Created by munsel on 13.09.15.
 * The HUD contains the pauseButton and the pause menu overlay
 * and also a label, that displays the current score
 */
public class HUD  extends Stage{

    private GameScreen screen;

    private Table table;
    private String scoreString;
    private Label scoreLabel;
    private ImageButton pauseButton;


    public HUD(GameScreen screen){
        super();
        this.screen = screen;
        table = new Table();
        table.setFillParent(true);
        table.pad(7f);

        TextureAtlas pauseButtonAtlas = new TextureAtlas("HUD/pauseButton.atlas");
        Skin pauseButtonImageSkin = new Skin(pauseButtonAtlas);
        ImageButton.ImageButtonStyle pauseButtonStyle = new ImageButton.ImageButtonStyle();
        pauseButtonStyle.imageUp = pauseButtonImageSkin.getDrawable("pause_up");
        pauseButtonStyle.imageDown = pauseButtonImageSkin.getDrawable("pause_down");

        pauseButton = new ImageButton(pauseButtonStyle);
        table.add(pauseButton).top().expand().pad(0.5f).left();
        pauseButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (HUD.this.screen.isPaused())
                    HUD.this.screen.resume();
                else
                    HUD.this.screen.pause();
            }
        });

        scoreString = "0";

        Label.LabelStyle scoreLabelStyle = new Label.LabelStyle();
        scoreLabelStyle.font = new BitmapFont(
                Gdx.files.internal("HUD/scoreFont.fnt"),
                Gdx.files.internal("HUD/scoreFont.png"), false
        );
        scoreLabel = new Label(scoreString, scoreLabelStyle);

        table.add(scoreLabel).top().right().pad(0.5f);
        table.setTouchable(Touchable.enabled);
        this.addActor(table);
    }

    public void update(float delta, int score){
        scoreString = Integer.toString(score);
        scoreLabel.setText(scoreString);
        this.act(delta);
    }
}
