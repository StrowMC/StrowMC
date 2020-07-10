package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionLeaderUUIDBean {

    private final UUID uuid;
    private final UUID leaderUuid;

    public FactionLeaderUUIDBean(UUID uuid, UUID leaderUuid) {
        this.uuid = uuid;
        this.leaderUuid = leaderUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getLeaderUuid() {
        return leaderUuid;
    }
}
