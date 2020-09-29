package fr.strow.persistence.beans;

import java.util.UUID;

public class NameBean {

    private final UUID uuid;
    private final String name;

    public NameBean(UUID uuid, String name) {
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
