package fr.strow.persistence.beans.factions;

import java.util.UUID;

public class FactionBean {

    private UUID uuid;
    private String name;
    private String prefix;
    private UUID leaderUuid;
    private String description;
    private int points;

    public FactionBean(UUID uuid, String name, String prefix, UUID leaderUuid, String description, int points) {
        this.uuid = uuid;
        this.name = name;
        this.prefix = prefix;
        this.leaderUuid = leaderUuid;
        this.description = description;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public UUID getLeaderUuid() {
        return leaderUuid;
    }

    public void setLeaderUuid(UUID leaderUuid) {
        this.leaderUuid = leaderUuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
