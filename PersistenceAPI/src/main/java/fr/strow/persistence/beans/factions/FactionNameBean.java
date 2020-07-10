package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionNameBean {

    private final UUID uuid;
    private final String name;

    public FactionNameBean(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
