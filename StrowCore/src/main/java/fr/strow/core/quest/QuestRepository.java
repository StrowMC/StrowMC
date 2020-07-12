package fr.strow.core.quest;

import fr.strow.core.utils.Utils;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Hokkaydo on 06-07-2020.
 */
public enum QuestRepository {

    QUEST_1(1, "Écrire un message dans le chat public", 1, AsyncPlayerChatEvent.class, e -> true);

    public static final Map<Class<? extends Event>, List<QuestRepository>> quests = new HashMap<>();

    private final int id;
    private final String name;
    private final int objective;
    private final Class<? extends Event> relativeEvent;
    private final Predicate<Event> questProcessor;

    QuestRepository(int id, String name, int objective, Class<? extends Event> relativeEvent, Predicate<Event> questProcessor) {
        this.id = id;
        this.name = name;
        this.objective = objective;
        this.relativeEvent = relativeEvent;
        this.questProcessor = questProcessor;

        addQuest();
    }

    public static Optional<QuestRepository> getById(int id) {
        return Arrays.stream(values()).filter(q -> q.id == id).findFirst();
    }

    private void addQuest() {
        Utils.updateMapList(relativeEvent, this, quests);
    }

    public Class<? extends Event> getRelativeEventClass() {
        return relativeEvent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getObjective() {
        return objective;
    }

    public boolean check(Event event) {
        return questProcessor.test(event);
    }
}
