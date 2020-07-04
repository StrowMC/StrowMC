/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:29
 */

package fr.strow.api;

import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public abstract class StrowPlugin extends JavaPlugin {

    protected Map<Class<? extends StrowModule>, StrowModule> modules;

    @Override
    public abstract void onEnable();

    @Override
    public abstract void onDisable();

    public Map<Class<? extends StrowModule>, StrowModule> getModules() {
        return modules;
    }

    protected void registerModules(StrowModule... modules) {
        for (StrowModule module : modules) {
            this.modules.put(module.getClass(), module);
        }
    }

    @SafeVarargs
    protected final void registerCommands(CommandsManager commandManager, Tuple<String, EvolvedCommand>... tuples) {
        for (Tuple<String, EvolvedCommand> tuple : tuples) {
            commandManager.registerCommand(tuple);
        }
    }

    protected void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
