package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionPrefixBean {

    private final UUID uuid;
    private final String prefix;

    public FactionPrefixBean(UUID uuid, String prefix) {
        this.uuid = uuid;
        this.prefix = prefix;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPrefix() {
        return prefix;
    }
}
