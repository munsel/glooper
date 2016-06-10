package de.glooper.game.SaveStateManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import de.glooper.game.Screens.GameScreen.WorldModel;

/**
 * Created by munsel on 15.09.15.
 *
 * reads a saved key-value file and returns its data
 * to build the game again when the app gets closed and reopened.
 *
 * it also saves all the necessary information that needs to be stored consistantly
 *
 *
 */
public class SettingsManager {
    private static final String TAG = SettingsManager.class.getSimpleName();

    /**
     * to check if the game is gameover or if there is a state to be loaded
     *
     * Key for boolean value. true, if not game over
     */
    public static final String GAME_STATUS = "status";


    /**
     * keys for getting float values
     */
    public static final String HERO_POS_X = "heroX";
    public static final String HERO_POS_Y = "heroY";
    public static final String HERO_ROT = "heroRot";

    public static final String TILE_NAME = "tileName";
    public static final String TILE_POS_X = "tileX";
    public static final String TILE_POS_Y = "tileY";




    private Preferences preferences;
    private final String PREFERENCES_NAME ="saveState";

    private SettingsState state;
    private WorldModel model;
    public SettingsManager(WorldModel model){
        this.model = model;


        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);

        preferences.putBoolean(GAME_STATUS, false);
        preferences.flush();

        boolean test = preferences.getBoolean(GAME_STATUS, true);
        Gdx.app.log(TAG+" test value is: ", String.valueOf(test) );


    }

    public void saveCurrentState(boolean gameOver){
        preferences.putBoolean(GAME_STATUS, gameOver);
        if (!gameOver){
            preferences.putFloat(HERO_POS_X, model.getHero().getPosition().x);
            preferences.putFloat(HERO_POS_Y, model.getHero().getPosition().y);
            preferences.putFloat(HERO_ROT, model.getHero().getRotation());

            preferences.putFloat(TILE_POS_X, model.getTilePos().x);
            preferences.putFloat(TILE_POS_Y, model.getTilePos().y);
            preferences.putString(TILE_NAME, model.getTileName());

            preferences.flush();

        }

    }

    public void loadStateFromPrefs(){

    }

}
