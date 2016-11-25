package de.glooper.game.ScoreBoardManagement;

import com.badlogic.gdx.utils.Array;

/**
 * holds all scoreboards for each level
 */
public class LevelScoreBoardsSafeState {
    public Array<ScoreboardSaveState> levelScoreBoards;

    public LevelScoreBoardsSafeState(){
        levelScoreBoards = new Array<ScoreboardSaveState>();
    }
}
