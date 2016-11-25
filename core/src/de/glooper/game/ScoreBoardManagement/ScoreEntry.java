package de.glooper.game.ScoreBoardManagement;

/**
 * just a single entry for the scoreboard
 * Created by munsel on 25.07.16.
 */
public class ScoreEntry {
    public int place;
    public String country;
    public String playerName;
    public int items;
    public int score;

    @Override
    public String toString() {
        String string = Integer.toString(place) + " " + playerName+ " "+ country +" "+Integer.toString(score);
        return string;
    }
}
