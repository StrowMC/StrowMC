package fr.strow.persistence.beans;

import java.util.UUID;

public class PseudoBean {

    private final UUID uuid;
    private final String pseudo;

    public PseudoBean(UUID uuid, String pseudo) {
        this.uuid = uuid;
        this.pseudo = pseudo;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPseudo() {
        return pseudo;
    }
}
