package de.glooper.game.ScoreBoardManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Created by munsel on 25.07.16.
 */
public class ScoreBoardLoader {
    private static final String TAG = ScoreBoardLoader.class.getSimpleName();
    /**
     * the Scoreboard
     * for level
     * @return 10 Entries
     */
    public static Array<ScoreEntry> getScoreEntries(int levelNum) {
        LevelScoreBoardsSafeState boards = getLevelScoreBoards();
        try{
            ScoreboardSaveState board = boards.levelScoreBoards.get(levelNum-1);
            return board.scoreEntries;

        }catch (IndexOutOfBoundsException e){
            Gdx.app.error(TAG, "no scoreboard present!");
        }
        Array<ScoreEntry> mockUpArray = new Array<ScoreEntry>();
        for(int i = 0; i<10 ; i++) {
            ScoreEntry entry = new ScoreEntry();
            entry.place = i;
            entry.country = "DE";
            entry.playerName = "bogus";
            entry.score =3*i;
            mockUpArray.add(entry);
        }
        return mockUpArray;
    }

    public static void setNewScoreEntry(int levelNum, ScoreEntry entry){
        Array<ScoreEntry> entries = getScoreEntries(levelNum);
        for (ScoreEntry entry1 : entries){
            if (entry.score > entry1.score){
                int index = entries.indexOf(entry1, true);
                entries.insert(index, entry);
                return;
            }
        }

    }


    private static final String SAVE_STATE_FILE_NAME = "scoreboards.json";

    public static void saveLevelScoreBoards(LevelScoreBoardsSafeState state){
        Json json = new Json();
        FileHandle handle = Gdx.files.local(SAVE_STATE_FILE_NAME);
        handle.writeString(json.prettyPrint(state), false);
        //handle.writeString(json.toJson(state), false);
    }

    public static LevelScoreBoardsSafeState getLevelScoreBoards(){
        Json json = new Json();
        try {
            FileHandle handle = Gdx.files.local(SAVE_STATE_FILE_NAME);
            String fileContent = handle.readString();
            //System.out.println(json.prettyPrint(fileContent));
            return json.fromJson(LevelScoreBoardsSafeState.class, fileContent);
        } catch (SerializationException e){
            Gdx.app.error(TAG, "could not read the JSON file or it did not exist!");
        } catch (GdxRuntimeException e){
            Gdx.app.error(TAG, "file not found YO!");
        }
        LevelScoreBoardsSafeState freshScoreBoardsSafestate = new LevelScoreBoardsSafeState();
        return freshScoreBoardsSafestate;
    }

}
