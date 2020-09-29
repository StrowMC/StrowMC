package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionWarpBean {

    private final UUID factionUuid;
    private final String name;
    private final int locationId;

    public FactionWarpBean(UUID factionUuid, String name, int locationId) {
        this.factionUuid = factionUuid;
        this.name = name;
        this.locationId = locationId;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }

    public String getName() {
        return name;
    }

    public int getLocationId() {
        return locationId;
    }
}
