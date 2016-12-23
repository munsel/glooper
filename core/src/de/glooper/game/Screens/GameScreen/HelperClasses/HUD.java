package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.glooper.game.ScoreBoardManagement.ScoreEntry;
import de.glooper.game.Screens.GameScreen.GameScreen;
import de.glooper.game.model.Heros.Hero;

/**
 * Created by munsel on 13.09.15.
 * The HUD contains the pauseButton and the pause menu overlay
 * and also a label, that displays the current score
 */
public class HUD  extends Stage{
    private final static String TAG = HUD.class.getSimpleName();

    private final float FADE_INTERVAL = 0.5f;

    private GameScreen screen;
    private Hero hero;

    private Table table;
    private SimpleStatusDrawer statusDrawer;

    private Table overlayMenuTable;
    private Label overlayHeader;
    private Button pauseButton;
    private TextButton actionButton;
    private Button backToParentButton;
    private ClickListener pauseListener;
    private ClickListener restartListener;
    private ClickListener resumeListener;


    private Skin skin;

    public HUD(final GameScreen screen, Hero hero){
        super();
        this.screen = screen;
        this.hero = hero;
        table = new Table();
        table.setFillParent(true);
        table.pad(7f);
        skin = AssetHandler.instance.skin;

        pauseListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.pause();
                pauseButton.setVisible(false);
                overlayHeader.setText("paused");
                actionButton.clearListeners();
                actionButton.setText("resume");
                actionButton.addListener(resumeListener);
                overlayMenuTable.setVisible(true);
                overlayMenuTable.addAction(Actions.sequence(Actions.alpha(0),Actions.alpha(1,FADE_INTERVAL)));
            }
        } ;
        resumeListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                overlayMenuTable.setVisible(false);
                pauseButton.setVisible(true);
                screen.resume();
            }
        };
        restartListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                overlayMenuTable.setVisible(false);
                pauseButton.setVisible(true);
                screen.show();
            }
        };

        pauseButton = new Button(skin, "pausebutton");
        pauseButton.addListener(pauseListener);

        statusDrawer = new SimpleStatusDrawer(hero, skin);
        table.setTouchable(Touchable.enabled);


        overlayMenuTable = new Table(skin);
        overlayMenuTable.setBackground(new TextureRegionDrawable(
                skin.getRegion("pausebg")));


        overlayHeader = new Label("paused", skin,"hud-headline");

        actionButton = new TextButton("restart", skin, "hud");
        actionButton.addListener(resumeListener);

        backToParentButton = new TextButton("back to menu", skin, "hud");
        backToParentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.backToMenu();
            }
        });

        overlayMenuTable.add(overlayHeader).center().top().padBottom(48);
        overlayMenuTable.row();
        overlayMenuTable.add(actionButton).center().padBottom(30);
        overlayMenuTable.row();
        overlayMenuTable.add(backToParentButton).center();

        table.add(pauseButton).top().pad(0.5f).left();
        table.add(statusDrawer).expandX().right().top();
        table.row();
        table.add(overlayMenuTable).colspan(2).expand().center();
        overlayMenuTable.setVisible(false);

        this.addActor(table);
    }

    public void gameOver(int finalScore){
        Gdx.app.log(TAG,"gameOver() called!");
        pauseButton.setVisible(false);
        statusDrawer.setVisible(false);
        overlayHeader.setText("game over");
        actionButton.clearListeners();
        actionButton.setText("restart");
        actionButton.addListener(restartListener);
        overlayMenuTable.setVisible(true);
        overlayMenuTable.addAction(Actions.alpha(0));
        overlayMenuTable.addAction(Actions.alpha(1,1));
        //overlayMenuTable.addAction(Actions.sequence(Actions.alpha(0),Actions.alpha(1,1.5f)));
    }


    public void restart(){
        //gameOverMenu.addAction(Actions.hide());
        pauseButton.setVisible(true);
        statusDrawer.setVisible(true);
        overlayMenuTable.setVisible(false);

    }

    public void update(float delta, int score){
        this.act(delta);
    }
}
