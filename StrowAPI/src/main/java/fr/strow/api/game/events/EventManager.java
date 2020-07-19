package fr.strow.api.game.events;

public interface EventManager {

    void start();

    void stop();

    boolean isEventStarted(Event event);

    boolean isEventStarted();
}
