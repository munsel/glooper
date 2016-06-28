package de.glooper.game.SaveStateManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

import java.io.FileNotFoundException;


/**
 * Created by munsel on 19.09.15.
 */
public class SaveStateManager {
    private static final String TAG = SaveStateManager.class.getSimpleName();

    private static final String SAVE_STATE_FILE_NAME = "savestate.json";

    public static void saveSaveState(SaveState state){
        Json json = new Json();
        FileHandle handle = Gdx.files.local(SAVE_STATE_FILE_NAME);
        handle.writeString(json.prettyPrint(state), false);
        //handle.writeString(json.toJson(state), false);
    }

    public static SaveState getSaveState(){
        Json json = new Json();
        try {
            FileHandle handle = Gdx.files.local(SAVE_STATE_FILE_NAME);
            String fileContent = handle.readString();
            //System.out.println(json.prettyPrint(fileContent));
            return json.fromJson(SaveState.class, fileContent);
        } catch (SerializationException e){
            Gdx.app.error(TAG, "could not read the JSON file or it did not exist!");
        } catch (GdxRuntimeException e){
            Gdx.app.error(TAG, "file not found YO!");
        }
        return new SaveState();
    }
}
