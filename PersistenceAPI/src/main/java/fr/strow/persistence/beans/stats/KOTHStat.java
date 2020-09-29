package fr.strow.persistence.beans.stats;

import java.util.UUID;

public class KOTHStat {

    private final UUID uuid;

    public KOTHStat(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
