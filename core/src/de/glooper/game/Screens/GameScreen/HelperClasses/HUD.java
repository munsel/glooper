package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.glooper.game.ScoreBoardManagement.ScoreBoardLoader;
import de.glooper.game.ScoreBoardManagement.ScoreEntry;
import de.glooper.game.Screens.GameScreen.GameScreen;
import de.glooper.game.model.Heros.Hero;

/**
 * Created by munsel on 13.09.15.
 * The HUD contains the pauseButton and the pause menu overlay
 * and also a label, that displays the current score
 */
public class HUD  extends Stage{

    private GameScreen screen;
    private Hero hero;

    private Table table;
    private String scoreString;
    private String playerName;
    private SimpleStatusDrawer statusDrawer;
    private Label scoreLabel;


    private Table gameOverMenu;
    private Label gameOverLabel;
    private Label gameOverMessageLabel;
    private Label finalScorelabel;
    private Button submitScoreButton;
    private Button backToParentButton;
    private Button newGameButton;
    private TextField playerNameTextField;

    private List<ScoreEntry> scoreEntryList;


    private Button pauseButton;
    private Skin skin;

    public HUD(final GameScreen screen, Hero hero){
        super();
        this.screen = screen;
        this.hero = hero;
        table = new Table();
        table.setFillParent(true);
        table.pad(7f);
        skin = AssetHandler.instance.skin;

        /*
        TextureAtlas pauseButtonAtlas = new TextureAtlas("HUD/pauseButton.atlas");
        Skin pauseButtonImageSkin = new Skin(pauseButtonAtlas);
        ImageButton.ImageButtonStyle pauseButtonStyle = new ImageButton.ImageButtonStyle();
        pauseButtonStyle.imageUp = pauseButtonImageSkin.getDrawable("pause_up");
        pauseButtonStyle.imageDown = pauseButtonImageSkin.getDrawable("pause_down");
        */

        pauseButton = new TextButton("pause", skin);
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
        /*
        Label.LabelStyle scoreLabelStyle = new Label.LabelStyle();
        scoreLabelStyle.font = new BitmapFont(
                Gdx.files.internal("HUD/scoreFont.fnt"),
                Gdx.files.internal("HUD/scoreFont.png"), false
        );*/

        statusDrawer = new SimpleStatusDrawer(hero, skin);
        table.add(statusDrawer).right();

        scoreLabel = new Label(scoreString, skin);

        table.add(scoreLabel).top().right().pad(0.5f);
        table.row();
        table.setTouchable(Touchable.enabled);

        gameOverMenu = new Table();

        gameOverLabel = new Label("Game Over!", skin);
        gameOverMessageLabel = new Label("you failed ):", skin);

        playerNameTextField = new TextField("bogus name", skin);


        submitScoreButton = new TextButton("submit score", skin);
        submitScoreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerName = playerNameTextField.getText();
                Gdx.app.log("submit score", "{score: "+scoreString+", name: "+playerName+"}");
            }
        });

        scoreEntryList = new List<ScoreEntry>(skin, "scoreboard");
        scoreEntryList.setItems(ScoreBoardLoader.getScoreEntries(1));
        ScrollPane scoreBoardListScroller = new ScrollPane(scoreEntryList);


        newGameButton = new TextButton("restart", skin);
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.show();
            }
        });

        backToParentButton = new TextButton("back", skin);
        backToParentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.backToMenu();
            }
        });
        finalScorelabel = new Label("final Score:##234", skin);
        gameOverMenu.add(gameOverLabel);
        gameOverMenu.row();
        gameOverMenu.add(gameOverMessageLabel);
        gameOverMenu.row();
        gameOverMenu.add(finalScorelabel);
        gameOverMenu.row();
        gameOverMenu.add(playerNameTextField).center().expandX().fillX();
        gameOverMenu.row();
        gameOverMenu.add(scoreBoardListScroller);
        gameOverMenu.row();
        gameOverMenu.add(submitScoreButton).right();
        gameOverMenu.row();
        gameOverMenu.add(newGameButton).left();
        gameOverMenu.add(backToParentButton).right();
        gameOverMenu.setVisible(false);

        table.add(gameOverMenu).expand().fillY().center();

        this.addActor(table);


    }

    public void gameOver(int finalScore){
        String finalScoreString = Integer.toString(finalScore);
        finalScorelabel.setText("final score: "+ finalScoreString);
        gameOverMenu.setVisible(true);
    }


    public void restart(){
        gameOverMenu.setVisible(false);
    }

    public void update(float delta, int score){
        scoreString = Integer.toString(score);
        scoreLabel.setText(scoreString);
        this.act(delta);
    }
}
