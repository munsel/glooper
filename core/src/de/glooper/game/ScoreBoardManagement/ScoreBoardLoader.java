package de.glooper.game.ScoreBoardManagement;

import com.badlogic.gdx.utils.Array;

/**
 * Created by munsel on 25.07.16.
 */
public class ScoreBoardLoader {
    /**
     * given the minimal Place of the Scoreboard
     * @return 10 Entries starting with minimal Place
     */
    public static Array<ScoreEntry> getScoreEntries(int minimalPlace) {
        Array<ScoreEntry> mockUpArray = new Array<ScoreEntry>();
        for(int i = 0; i<10 ; i++) {
            ScoreEntry entry = new ScoreEntry();
            entry.place = i;
            entry.country = "DE";
            entry.playerName = "boogus";
            entry.score =3*i;
            mockUpArray.add(entry);
        }
        return mockUpArray;
    }

}
