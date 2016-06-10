package de.glooper.game.SaveStateManagement;

/**
 * Created by munsel on 15.09.15.
 *
 * a Savestate instance represents all the data needed to be accessed
 *
 * settings and so on
 */
public class SettingsState {


    private String theme;
    private boolean musicOn;
    private boolean soundsOn;
    private float musicVolume;
    private float soundVolume;

    private static SettingsState instance;

    public static SettingsState getSettings(){
        if (instance == null){
            instance = new SettingsState();
        }
        return instance;
    }

    private SettingsState(){};



    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }



    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public boolean isSoundsOn() {
        return soundsOn;
    }

    public void setSoundsOn(boolean soundsOn) {
        this.soundsOn = soundsOn;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }



}
