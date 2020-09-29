package fr.strow.api.game.moderation;

import java.sql.Timestamp;

public interface DatedSanction {

    Timestamp getDate();

    default boolean isActive() {
        return false;
    }
}
