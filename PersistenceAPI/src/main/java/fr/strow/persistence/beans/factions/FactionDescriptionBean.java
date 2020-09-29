package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionDescriptionBean {

    private final UUID uuid;
    private final String description;

    public FactionDescriptionBean(UUID uuid, String description) {
        this.uuid = uuid;
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDescription() {
        return description;
    }
}
