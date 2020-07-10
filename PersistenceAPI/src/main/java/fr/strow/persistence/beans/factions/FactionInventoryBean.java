package fr.strow.persistence.beans.factions;

import java.io.InputStream;
import java.util.UUID;

public class FactionInventoryBean {

    private final UUID uuid;
    private final InputStream content;

    public FactionInventoryBean(UUID uuid, InputStream content) {
        this.uuid = uuid;
        this.content = content;
    }

    public UUID getUuid() {
        return uuid;
    }

    public InputStream getContent() {
        return content;
    }
}
