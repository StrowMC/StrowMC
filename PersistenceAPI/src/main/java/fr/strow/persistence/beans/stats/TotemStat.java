package fr.strow.persistence.beans.stats;

import java.util.UUID;

public class TotemStat {

    private final UUID uuid;

    public TotemStat(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
