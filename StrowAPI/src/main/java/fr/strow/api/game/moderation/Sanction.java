package fr.strow.api.game.moderation;

import java.util.List;

public interface Sanction<T> {

    void add(T sanction);

    List<T> get();
}
