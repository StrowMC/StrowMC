package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionHomeBean {

    private final UUID factionUuid;
    private final int locationId;

    public FactionHomeBean(UUID factionUuid, int locationId) {
        this.factionUuid = factionUuid;
        this.locationId = locationId;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }

    public int getLocationId() {
        return locationId;
    }
}
