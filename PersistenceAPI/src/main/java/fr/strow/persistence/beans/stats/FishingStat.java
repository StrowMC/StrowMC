package fr.strow.persistence.beans.stats;

import java.util.UUID;

public class FishingStat {

    private final UUID uuid;

    public FishingStat(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
