package fr.strow.persistence.beans.factions.profile;

import java.util.UUID;

public class FactionProfileBean {

    private final UUID uuid;
    private final UUID factionUuid;
    private final int roleId;
    private final int power;
    private final boolean claimer;

    public FactionProfileBean(UUID uuid, UUID factionUuid, int roleId, int power, boolean claimer) {
        this.uuid = uuid;
        this.factionUuid = factionUuid;
        this.roleId = roleId;
        this.power = power;
        this.claimer = claimer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }

    public int getRoleId() {
        return roleId;
    }

    public int getPower() {
        return power;
    }

    public boolean isClaimer() {
        return claimer;
    }
}
