/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:47
 */

package fr.strow.api.modules;

import com.google.inject.Injector;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.properties.AbstractProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class StrowModule {

    protected final Injector injector;

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract List<Listener> getListeners();

    public abstract List<Tuple<String, EvolvedCommand>> getCommands();

    public abstract List<AbstractConfiguration> getConfigurations();

    public abstract List<AbstractProperty> getProperties();

    public StrowModule(Injector injector){
        this.injector = injector;
    }

}
