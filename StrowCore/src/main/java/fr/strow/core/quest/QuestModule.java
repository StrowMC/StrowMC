package fr.strow.core.quest;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.Property;
import fr.strow.api.service.MessageService;
import fr.strow.core.quest.command.QuestCommand;
import fr.strow.core.quest.configuration.QuestConfiguration;
import fr.strow.core.quest.listener.QuestListener;
import fr.strow.core.quest.property.QuestProperty;
import fr.strow.core.utils.Utils;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Hokkaydo on 06-07-2020.
 */
public class QuestModule extends StrowModule {

    private final Map<Class<? extends Event>, List<QuestRepository>> quests = new HashMap<>();

    private final PlayerManager playerManager;
    private final MessageService messageService;

    private final Injector injector;

    public QuestModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        this.playerManager = injector.getInstance(PlayerManager.class);
        this.messageService = injector.getInstance(MessageService.class);
        this.injector = injector;

        for (QuestRepository value : QuestRepository.values()) {
            Utils.updateMapList(value.getRelativeEventClass(), value, quests);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public List<Listener> getListeners() {
        return Collections.singletonList(new QuestListener(this));
    }

    @Override
    public List<AbstractConfiguration> getConfigurations() {
        return Collections.singletonList(injector.getInstance(QuestConfiguration.class));
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Collections.singletonList(
                Tuple.of("quest", injector.getInstance(QuestCommand.class))
        );
    }

    @Override
    public List<Class<? extends Property>> getProperties() {
        return Collections.singletonList(QuestProperty.class);
    }

    public <T extends Event> void processQuests(T event) {
        Optional.ofNullable(quests.get(event.getClass())).ifPresent(list -> list.forEach(quest -> {
            if (quest.check(event)) {
                playerManager.getPlayers().forEach(strowPlayer -> strowPlayer.getOptionalProperty(QuestProperty.class).ifPresent(property -> {
                    if (property.getQuestId() == quest.getId()) {
                        property.incrementCurrent();
                        if (property.getCurrent() == quest.getObjective()) {
                            messageService.sendMessage(property.getUuid(), QuestConfiguration.QUEST_ENDED_MESSAGE, quest.getName());
                            property.endQuest();
                        }
                    }
                }));
            }
        }));
    }
}
