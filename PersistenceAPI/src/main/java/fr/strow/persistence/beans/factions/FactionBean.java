package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionBean {

    private UUID uuid;
    private String name;
    private String description;
    private UUID leaderUuid;
    private int points;

    public FactionBean(UUID uuid, String name, String description, UUID leaderUuid, int points) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.leaderUuid = leaderUuid;
        this.points = points;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getLeaderUuid() {
        return leaderUuid;
    }

    public void setLeaderUuid(UUID leaderUuid) {
        this.leaderUuid = leaderUuid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
