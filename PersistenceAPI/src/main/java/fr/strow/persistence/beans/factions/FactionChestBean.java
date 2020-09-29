package fr.strow.persistence.beans.factions;

import java.io.InputStream;
import java.util.UUID;

public class FactionChestBean {

    private final UUID factionUuid;
    private final InputStream content;

    public FactionChestBean(UUID factionUuid, InputStream content) {
        this.factionUuid = factionUuid;
        this.content = content;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }

    public InputStream getContent() {
        return content;
    }
}
