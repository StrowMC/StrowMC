package fr.strow.api.game.moderation;

import java.sql.Timestamp;

public class Mute implements DatedSanction {

    @Override
    public Timestamp getDate() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
