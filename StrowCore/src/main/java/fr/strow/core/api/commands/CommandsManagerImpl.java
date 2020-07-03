/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:19
 */

package fr.strow.core.api.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandsManager;
import me.choukas.commands.EvolvedCommand;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommandsManagerImpl implements CommandsManager {

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
    public void registerCommand(String name, EvolvedCommand command) {
        commands.put(name, command);
        commandMap.register(name, command);
    }

    @Override
    public void unregisterCommand(String name) {
        commandMap.getCommand(name).unregister(commandMap);
    }

    @Override
    public Map<String, EvolvedCommand> getCommands() {
        return commands;
    }
}
