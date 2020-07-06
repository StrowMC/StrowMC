package fr.strow.core.quest.property;

import fr.strow.api.game.AbstractProperty;
import fr.strow.api.properties.OptionalPersistentProperty;

import java.util.UUID;

/**
 * Created by Hokkaydo on 06-07-2020.
 */
public class QuestProperty implements OptionalPersistentProperty, AbstractProperty {

    private final UUID uuid;
    private int current = 0;
    private int questId;

    public QuestProperty(UUID uuid, int questId) {
        this.uuid = uuid;
        this.questId = questId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCurrent() {
        return current;
    }

    public void incrementCurrent() {
        current++;
    }

    public int getQuestId() {
        return questId;
    }

    public void endQuest() {
        questId++;
        current = 0;
    }

    //TODO ask choukas for persistence
    @Override
    public boolean has(UUID uuid) {
        return false;
    }

    //TODO ask choukas for persistence
    @Override
    public void save(UUID uuid) {

    }
}
