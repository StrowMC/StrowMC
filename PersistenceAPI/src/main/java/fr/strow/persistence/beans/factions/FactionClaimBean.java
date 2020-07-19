package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionClaimBean {

    private final int id;
    private final UUID factionUuid;
    private final String world;
    private final int x;
    private final int z;

    public FactionClaimBean(int id, UUID factionUuid, String world, int x, int z) {
        this.id = id;
        this.factionUuid = factionUuid;
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
