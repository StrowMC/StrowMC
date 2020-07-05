/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:47
 */

package fr.strow.api.modules;

import fr.strow.api.commands.CommandsManager;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.Property;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public abstract class StrowModule {

    private final JavaPlugin plugin;
    private final CommandsManager commandsManager;
    private final PropertiesHandler propertiesHandler;

    public StrowModule(JavaPlugin plugin, CommandsManager commandsManager, PropertiesHandler propertiesHandler) {
        this.plugin = plugin;
        this.commandsManager = commandsManager;
        this.propertiesHandler = propertiesHandler;
    }

    public void onEnable() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        for (Listener listener : getListeners()) {
            pluginManager.registerEvents(listener, plugin);
        }

        for (Tuple<String, EvolvedCommand> command : getCommands()) {
            commandsManager.registerCommand(command);
        }

        for (Condition condition : getConditions()) {
            commandsManager.registerCondition(condition);
        }

        for (Class<? extends Property> property : getProperties()) {
            propertiesHandler.registerProperty(property);
        }
    }

    public void onDisable() {
        for (Listener listener : getListeners()) {
            HandlerList.unregisterAll(listener);
        }

        for (Tuple<String, EvolvedCommand> command : getCommands()) {
            commandsManager.unregisterCommand(command.getValue());
        }

        for (Class<? extends Property> property : getProperties()) {
            propertiesHandler.registerProperty(property);
        }
    }

    public List<Listener> getListeners() {
        return Collections.emptyList();
    }

    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Collections.emptyList();
    }

    public List<Condition> getConditions() {
        return Collections.emptyList();
    }

    public List<Parameter<?>> getParameters() {
        return Collections.emptyList();
    }

    public List<AbstractConfiguration> getConfigurations() {
        return Collections.emptyList();
    }

    public List<Class<? extends Property>> getProperties() {
        return Collections.emptyList();
    }
}
