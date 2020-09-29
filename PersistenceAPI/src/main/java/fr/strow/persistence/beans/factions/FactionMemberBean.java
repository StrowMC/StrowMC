package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionMemberBean {

    private final UUID uuid;
    private final UUID factionUuid;

    public FactionMemberBean(UUID uuid, UUID factionUuid) {
        this.uuid = uuid;
        this.factionUuid = factionUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }
}
