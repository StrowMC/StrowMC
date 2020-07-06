/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:19
 */

package fr.strow.core.api.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandsCollection;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.commands.ConditionsCollection;
import fr.strow.api.commands.ParametersCollection;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.Requirement;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommandsManagerImpl implements CommandsManager {

    private static final CommandsCollection commandsCollection = new CommandsCollection();
    private static final ConditionsCollection conditionsCollection = new ConditionsCollection();
    private static final ParametersCollection parametersCollection = new ParametersCollection();

    private CommandMap commandMap;
    private final Map<String, EvolvedCommand> commands = new HashMap<>();

    @Inject
    public CommandsManagerImpl(PluginManager pluginManager) {
        initCommandMap(pluginManager);
    }

    private void initCommandMap(PluginManager pluginManager) {
        try {
            Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            commandMap = (CommandMap) commandMapField.get(pluginManager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerCommand(EvolvedCommand command) {
        commandsCollection.registerCommand(command);
    }

    @Override
    public void registerCommand(String name, EvolvedCommand command) {
        commands.put(name, command);
        commandMap.register(name, command);

        registerCommand(command);
    }

    @Override
    public void unregisterCommand(EvolvedCommand command) {
        commandsCollection.unregisterCommand(command);
    }

    @Override
    public <T extends EvolvedCommand> T getCommand(Class<T> command) {
        return commandsCollection.getCommand(command);
    }

    @Override
    public void registerCondition(Requirement requirement) {
        conditionsCollection.registerCondition(requirement);
    }

    @Override
    public void unregisterCondition(Requirement requirement) {
        conditionsCollection.unregisterCondition(requirement);
    }

    @Override
    public <T extends Requirement> T getCondition(Class<T> condition) {
        return conditionsCollection.getCondition(condition);
    }

    @Override
    public void registerParameter(Parameter<?> parameter) {
        parametersCollection.registerParameter(parameter);
    }

    @Override
    public void unregisterParameter(Parameter<?> parameter) {
        parametersCollection.unregisterParameter(parameter);
    }

    @Override
    public <T extends Parameter<?>> T getParameter(Class<T> parameter) {
        return parametersCollection.getParameter(parameter);
    }

    @Override
    public Map<String, EvolvedCommand> getCommands() {
        return commands;
    }
}
