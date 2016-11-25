package de.glooper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.glooper.game.Screens.GameScreen.GameScreen;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;
import de.glooper.game.Screens.MenuScreen.MenuScreen;
import de.glooper.game.Screens.SplashScreen.SplashScreen;

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
	private SplashScreen splashScreen;

	/**
	 * a PurchaseHandler for cross-platform
	 * in-app purchases
	 */


	public void create () {
		Gdx.graphics.setWindowedMode(1280, 720);
		AssetManager assetManager = new AssetManager();
		AssetHandler.instance.startInit(assetManager);
		//setScreen(new GameScreen(this));
		splashScreen = new SplashScreen(this, assetManager);
		setScreen(splashScreen);

		//setScreen(menuScreen);

	}

	public void backToMenu(){setScreen(menuScreen);}

	public void startGame(){
		setScreen(gameScreen);
	}

	public void createAssetsInstance(){
		AssetHandler.instance.finishInit();
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
	}

}
