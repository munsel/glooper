package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import de.glooper.game.Screens.GameScreen.HelperClasses.Assets.ATextureAsset;
import de.glooper.game.Screens.GameScreen.HelperClasses.Assets.SimpleTiles.TileAsset;
import de.glooper.game.Screens.GameScreen.HelperClasses.Assets.SimpleTiles.XCross;
import de.glooper.game.Screens.GameScreen.HelperClasses.Assets.Heros.Glooper;
import de.glooper.game.model.TileSets.ITileSet;
import de.glooper.game.model.TileSets.StarterTiles;

import java.util.HashMap;
import java.util.stream.Stream;


/**
 * Created by munsel on 01.08.15. 
 */
public class AssetHandler implements Disposable, AssetErrorListener {
    private static final String TAG = AssetHandler.class.getSimpleName();
    /**
     * this AssetHandler is handling all the Assets:
     * Textures
     * SoundEffects and Music
     *
     * It loads and frees the corresponding Asset in runtime
     * this is a critical part of our App, because it handles
     * all the stuff that drains the memory!
     */

    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";


    public static  AssetHandler instance = new AssetHandler();


    /**
     * these constants are the file descriptors for the
     * WorldTiles.simple worldTiles set
     */

    public static final String simpleTilesDirectoryName = "WorldTiles/starters/";

    private AssetManager assetManager;

    public ATextureAsset firstWorldTileAsset;
    public ATextureAsset secondWolrdTileAsset;

    public HashMap<String, ATextureAsset> tileAssets;
    public TextureRegion[] clouds;

    public TextureAtlas seaweed;
    public TextureAtlas eel;

    public ITileSet starterTiles;

    public Skin skin;

    public Glooper glooperAsset;

    private String directoryName;

    private AssetHandler(){}

    public void startInit(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);

        directoryName = simpleTilesDirectoryName;
        /**this would initiate every available map which is 2048px * 2048px
         * ...so, we do not want this to happen
         *
         * .... ok fuck it! we load the whole set!
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
        //assetManager.load(directoryName + "xCross.png", Texture.class);
        /**
         * this needs to be loaded first, so that the splashScreen can show something!
         */
        assetManager.load("Heros/glooper.pack", TextureAtlas.class);
        assetManager.finishLoading();

        starterTiles = new StarterTiles();
        String[] starterTileSetNames = starterTiles.getAllTextureFileNames();
        for (int i = 0; i<starterTileSetNames.length; i++ ){
            assetManager.load(directoryName+ starterTileSetNames[i]+".png", Texture.class);
        }


        assetManager.load("Backgrounds/clouds.atlas", TextureAtlas.class);
        assetManager.load("Entities/seaweed.pack", TextureAtlas.class);
        assetManager.load("Entities/eel.atlas", TextureAtlas.class);
        /**
         * but now we must direct the corresponding
         * Textures handled by the assetManager
         * ... in runtime
         */

       // Texture startTileTexture = assetManager.get(directoryName+"xCross.png", Texture.class);
        //startTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //firstWorldTileAsset = new XCross(startTileTexture);
    }

    public void finishInit(){
        tileAssets = new HashMap<String, ATextureAsset>();
        String[] starterTileSetNames = starterTiles.getAllTextureFileNames();
        for (int i = 0; i<starterTileSetNames.length; i++ ){
            Texture tileTexture = assetManager.get(directoryName+starterTileSetNames[i]+".png", Texture.class);
            tileAssets.put(starterTileSetNames[i], new TileAsset(tileTexture));
        }
        TextureAtlas glooperAtlas = assetManager.get("Heros/glooper.pack");
        glooperAsset = new Glooper(glooperAtlas);

        TextureAtlas cloudsAtlas = assetManager.get("Backgrounds/clouds.atlas");
        clouds = new TextureRegion[6];
        for (int i = 0; i<6; i++){
            clouds[i] = cloudsAtlas.findRegion("cloud"+Integer.toString(i+1));
        }

        seaweed = assetManager.get("Entities/seaweed.pack");
        eel = assetManager.get("Entities/eel.atlas");

        skin = initSkin();
    }

    public void loadNewTile(String tileDescriptor ){
        assetManager.load(tileDescriptor, Texture.class);
        //assetManager.finishLoading();

    }

    public void unloadOldTile ( String tileDescriptor){
        assetManager.unload(tileDescriptor);

    }

    private Skin initSkin(){
        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        int[] fontsizes = {24, 36};
        BitmapFont[] fonts = getFonts("Roboto-Regular.ttf", fontsizes);
        skin.add("fontawesome-24", fonts[0]);
        skin.add("fontawesome-36", fonts[1]);
        skin.load(Gdx.files.internal("uiskin.json"));
        return skin;
    }



    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    /**
     * to generate some BitmapFonts on the fly
     */
    private BitmapFont[] getFonts(String filename, int[] sizes) {
        FileHandle handle = Gdx.files.internal(filename);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(handle);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARACTERS;
        BitmapFont[] fonts = new BitmapFont[2];
        for ( int i = 0 ; i < sizes.length ; i++){
            parameter.size = sizes[i];
            fonts[i]=generator.generateFont(parameter);
        }
        generator.dispose();
        return fonts;
    }

    @Override
    public void dispose() {
        assetManager.dispose();

    }
}
