package fr.strow.persistence.beans.quests;

import java.util.UUID;

public class QuestProgressBean {

    private final UUID uuid;
    private final int id;
    private final int progress;

    public QuestProgressBean(UUID uuid, int id, int progress) {
        this.uuid = uuid;
        this.id = id;
        this.progress = progress;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }
}
