package de.glooper.game.SaveStateManagement;

/**
 * Created by munsel on 21.06.16.
 */
public interface Safeable {
    void save(SaveState saveState);
    void load(SaveState saveState);
}
