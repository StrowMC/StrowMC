package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionPointsBean {

    private final UUID uuid;
    private final int points;

    public FactionPointsBean(UUID uuid, int points) {
        this.uuid = uuid;
        this.points = points;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPoints() {
        return points;
    }
}
