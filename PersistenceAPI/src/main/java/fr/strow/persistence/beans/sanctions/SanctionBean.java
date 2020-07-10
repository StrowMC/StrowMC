package fr.strow.persistence.beans.sanctions;

import java.sql.Timestamp;
import java.util.UUID;

public abstract class SanctionBean {

    private final UUID uuid;
    private final String reason;
    private final UUID sanctionerUuid;
    private final Timestamp startingTimestamp;
    private final Timestamp endingTimestamp;

    public SanctionBean(UUID uuid, String reason, UUID sanctionerUuid, Timestamp startingTimestamp, Timestamp endingTimestamp) {
        this.uuid = uuid;
        this.reason = reason;
        this.sanctionerUuid = sanctionerUuid;
        this.startingTimestamp = startingTimestamp;
        this.endingTimestamp = endingTimestamp;
    }

    public UUID getUuid() {
        return uuid;
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
}
