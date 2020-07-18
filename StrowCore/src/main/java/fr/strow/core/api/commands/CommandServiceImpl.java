/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:19
 */

package fr.strow.core.api.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.Parameter;
import me.choukas.commands.api.Requirement;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommandServiceImpl implements CommandService {

    private final Injector injector;

    private CommandMap commandMap;
    private final Map<String, EvolvedCommand> commands = new HashMap<>();

    @Inject
    public CommandServiceImpl(Injector injector) {
        this.injector = injector;

        initCommandMap(injector.getInstance(PluginManager.class));
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
    public void registerCommand(Class<? extends EvolvedCommand> command) {
        EvolvedCommand implementation = injector.getInstance(command);
        String name = implementation.getName();

        commands.put(name, implementation);
        commandMap.register(name, implementation);
    }

    @Override
    public void unregisterCommand(Class<? extends EvolvedCommand> command) {
        EvolvedCommand implementation = injector.getInstance(command);
        String name = implementation.getName();

        commands.remove(name);
        commandMap.getCommand(name).unregister(commandMap);
    }


    @Override
    public void unregisterCommand(String name) {
        commandMap.getCommand(name).unregister(commandMap);
    }

    @Override
    public <T extends EvolvedCommand> T getCommand(Class<T> command) {
        return injector.getInstance(command);
    }

    @Override
    public <T extends Requirement> T getRequirement(Class<T> condition) {
        return injector.getInstance(condition);
    }
    @Override
    public <T extends Parameter<?>> T getParameter(Class<T> parameter) {
        return injector.getInstance(parameter);
    }

    @Override
    public Map<String, EvolvedCommand> getCommands() {
        return commands;
    }
}
