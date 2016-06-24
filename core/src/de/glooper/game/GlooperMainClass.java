package de.glooper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.glooper.game.Screens.GameScreen.GameScreen;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.Screens.MenuScreen.MenuScreen;

public class GlooperMainClass extends Game {

	private static final String TAG = GlooperMainClass.class.getSimpleName();

	/**
	* This is the MainClass, which handles all the Screens
	 * and transitions between them.
	*
	*
	 */
	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	public void create () {
		Gdx.graphics.setDisplayMode(800, 480, false);
		AssetHandler.instance.init(new AssetManager());
		//setScreen(new GameScreen(this));
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(menuScreen);

	}

	public void backToMenu(){setScreen(menuScreen);}

	public void startGame(){
		setScreen(gameScreen);
	}

}
