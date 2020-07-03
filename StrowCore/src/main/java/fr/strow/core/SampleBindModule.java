/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:33
 */

package fr.strow.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.strow.api.StrowPlugin;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.modules.Modules;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.Properties;
import fr.strow.api.properties.PropertiesCollection;
import fr.strow.api.utils.Scheduler;
import fr.strow.core.api.commands.CommandsManagerImpl;
import fr.strow.core.api.utils.SchedulerImpl;
import fr.strow.core.module.economy.EconomyModule;
import fr.strow.core.module.faction.FactionManagerImpl;
import fr.strow.core.module.faction.FactionModule;
import fr.strow.core.module.player.PlayerManagerImpl;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SampleBindModule extends AbstractModule {

    private static final List<Class<? extends StrowModule>> modules = new ArrayList<>();
    private static final PropertiesCollection properties = new PropertiesCollection();

    static {
        modules.add(EconomyModule.class);
        modules.add(FactionModule.class);
    }

    private final StrowPlugin plugin;

    public SampleBindModule(StrowPlugin plugin) {
        this.plugin = plugin;
    }

    @Provides
    @Modules
    public List<Class<? extends StrowModule>> getModules() {
        return modules;
    }

    @Provides
    @Properties
    public PropertiesCollection getProperties() {
        return properties;
    }

    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(plugin);
        bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());

        bind(CommandsManager.class).to(CommandsManagerImpl.class);
        bind(FactionManager.class).to(FactionManagerImpl.class);
        bind(PlayerManager.class).to(PlayerManagerImpl.class);
        bind(Scheduler.class).to(SchedulerImpl.class);
    }
}
