package de.glooper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import de.glooper.game.Screens.GameScreen.GameScreen;
import de.glooper.game.Screens.MenuScreen.MenuScreen;

public class GlooperMainClass extends Game {

	private static final String TAG = GlooperMainClass.class.getSimpleName();

	/**
	* This is the MainClass, which handles all the Screens
	 * and transitions between them.
	*
	*
	 */
	public void create () {
		Gdx.graphics.setDisplayMode(800, 480, false);
		//setScreen(new GameScreen(this));

		setScreen(new MenuScreen(this));


	}

	public void startGame(){
		setScreen(new GameScreen(this));
	}

}
