package de.glooper.game.Interfaces;

/**
 * Created by munsel on 15.06.15.
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
