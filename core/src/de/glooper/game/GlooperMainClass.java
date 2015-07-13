package de.glooper.game;

import com.badlogic.gdx.Game;
import de.glooper.game.Screens.GameScreen.GameScreen;

public class GlooperMainClass extends Game {

	private static final String TAG = GlooperMainClass.class.getSimpleName();

	/**
	* This is the MainClass, which handles all the Screens
	 * and transitions between them.
	*
	*
	 */
	public void create () {
		setScreen(new GameScreen(this));


	}

}
