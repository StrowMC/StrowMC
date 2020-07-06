package fr.strow.core.quest.configuration;

import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.configuration.Config;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class QuestConfiguration extends AbstractConfiguration {

    @Config("endquest_message")
    public static String QUEST_ENDED_MESSAGE = "§aVous avez fini la quête %s";

    public QuestConfiguration() {
        super("quests");
    }
}
