package fr.strow.api.game.moderation;

import java.sql.Timestamp;
import java.util.UUID;

public class Ban implements DatedSanction {

    private final String reason;
    private final UUID sanctionerUuid;
    private final Timestamp startingTimestamp;
    private final Timestamp endingTimestamp;

    public Ban(String reason, UUID sanctionerUuid, Timestamp startingTimestamp, Timestamp endingTimestamp) {
        this.reason = reason;
        this.sanctionerUuid = sanctionerUuid;
        this.startingTimestamp = startingTimestamp;
        this.endingTimestamp = endingTimestamp;
    }

    public String getReason() {
        return reason;
    }

    public UUID getSanctionerUuid() {
        return sanctionerUuid;
    }

    public Timestamp getStartingTimestamp() {
        return startingTimestamp;
    }

    public Timestamp getEndingTimestamp() {
        return endingTimestamp;
    }

    @Override
    public Timestamp getDate() {
        return startingTimestamp;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
