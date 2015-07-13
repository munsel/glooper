package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;

/**
 * Created by munsel on 06.06.15.
 */
public class GameStage extends Overlap2DStage {

    private ResourceManager rm;

    public GameStage(ResourceManager resourceManager){
        super();
        this.rm = resourceManager;

        initSceneLoader();


        sceneLoader.loadScene("MainScene");
        addActor(sceneLoader.getRoot());
    }
}
