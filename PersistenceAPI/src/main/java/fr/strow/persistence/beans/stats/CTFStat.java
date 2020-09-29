package fr.strow.persistence.beans.stats;

import java.util.UUID;

public class CTFStat {

    private final UUID uuid;

    public CTFStat(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
