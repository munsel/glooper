package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.HelperClasses.Assets.ATextureAsset;
import de.glooper.game.Screens.GameScreen.HelperClasses.Assets.SimpleTiles.XCross;


/**
 * Created by munsel on 01.08.15. 
 */
public class AssetHandler implements Disposable, AssetErrorListener {
    /**
     * this AssetHandler is handling all the Assets:
     * Textures
     * SoundEffects and Music
     *
     * It loads and frees the corresponding Asset in runtime
     * this is a critical part of our App, because it handles
     * all the stuff that drains the memory!
     */
    private static final String TAG = AssetHandler.class.getSimpleName();

    public static  AssetHandler instance = new AssetHandler();



    /**
     * these constants are the file descriptors for the
     * WorldTiles.simple worldTiles set
     */

    public static final String simpleTilesDirectoryName = "WorldTiles/simple/";
    public static final String[] names={"leftTurn", "rightTurn",
            "tCross", "xCross", "straight"};



    private AssetManager assetManager;

    public ATextureAsset firstWorldTileAsset;
    public ATextureAsset secondWolrdTileAsset;

    private String directoryName;

    private AssetHandler(){}

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);

        directoryName = simpleTilesDirectoryName;
        /**this would initiate every available map which is 2048px * 2048px
         * ...so, we do not want this to happen
         */
        //for(int i = 0; i < names.length; i++){
          //  assetManager.load(simpleTilesDirectoryName+names[i], Texture.class);
        //}
        /**
         * now there is an alternative:
         * just initiate the start tile Texture
         * so only one image of 2048px * 2048px in merory
         * therefore, a x cross with four openings will be used
         */
        assetManager.load(directoryName+"xCrossSmall.png",Texture.class);
        /**
         * but now we must direct the corresponding
         * Textures handled by the assetManager
         * ... in runtime
         */
        assetManager.finishLoading();
        Texture startTileTexture = assetManager.get(directoryName+"xCrossSmall.png", Texture.class);
        startTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        firstWorldTileAsset = new XCross(startTileTexture);

    }

    public void loadNewTile(String tileDescriptor ){
        assetManager.load(tileDescriptor, Texture.class);
        assetManager.finishLoading();

    }

    public void unloadOldTile ( String tileDescriptor){
        assetManager.unload(tileDescriptor);

    }



    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {
        assetManager.dispose();

    }
}
