package fr.strow.api.game.moderation;

public interface Sanctions {

    <T extends Sanction<T>> T get(Class<T> sanction);
}
