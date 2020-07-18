package fr.strow.api.game.moderation;

import java.sql.Timestamp;
import java.util.UUID;

public class Kick implements DatedSanction {

    private final String reason;
    private final UUID sanctionerUuid;
    private final Timestamp date;

    public Kick(String reason, UUID sanctionerUuid, Timestamp date) {
        this.reason = reason;
        this.sanctionerUuid = sanctionerUuid;
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public UUID getSanctionerUuid() {
        return sanctionerUuid;
    }

    public Timestamp getTimestamp() {
        return date;
    }

    @Override
    public Timestamp getDate() {
        return date;
    }
}
