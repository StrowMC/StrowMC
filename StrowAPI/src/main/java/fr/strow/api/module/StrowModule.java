/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:47
 */

package fr.strow.api.module;

import fr.strow.api.commands.CommandService;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.property.Property;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.api.property.PropertiesOwner;
import me.choukas.commands.EvolvedCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public abstract class StrowModule {

    private final JavaPlugin plugin;
    private final CommandService commandService;
    private final PropertiesHandler propertiesHandler;

    public StrowModule(JavaPlugin plugin, CommandService commandService, PropertiesHandler propertiesHandler) {
        this.plugin = plugin;
        this.commandService = commandService;
        this.propertiesHandler = propertiesHandler;
    }

    public void onEnable() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        for (AbstractConfiguration configuration : getConfigurations()) {
            configuration.loadConfig();
        }

        for (Listener listener : getListeners()) {
            pluginManager.registerEvents(listener, plugin);
        }

        for (Class<? extends EvolvedCommand> command : getCommands()) {
            commandService.registerCommand(command);
        }
    }

    public void onDisable() {
        for (Listener listener : getListeners()) {
            HandlerList.unregisterAll(listener);
        }

        for (Class<? extends EvolvedCommand> command : getCommands()) {
            commandService.unregisterCommand(command);
        }

        for (AbstractConfiguration configuration : getConfigurations()) {
            configuration.saveConfig();
        }
    }

    protected <O extends PropertiesOwner<O>, P extends Property<O>> void bindProperty(Class<O> owner, Class<P> property, Class<? extends ImplementationProperty<P>> implementation) {
      propertiesHandler.bindProperty(owner, property, implementation);
    }

    protected <T extends PropertiesOwner<T>> void unbindProperty(Class<T> owner, Class<? extends Property<T>> property) {
        propertiesHandler.unbindProperty(owner, property);
    }

    protected <T extends PropertiesOwner<T>> void unbindProperties(Class<T> owner) {
        propertiesHandler.unbindProperties(owner);
    }

    protected List<Listener> getListeners() {
        return Collections.emptyList();
    }

    protected List<Class<? extends EvolvedCommand>> getCommands() {
        return Collections.emptyList();
    }

    protected List<AbstractConfiguration> getConfigurations() {
        return Collections.emptyList();
    }
}
