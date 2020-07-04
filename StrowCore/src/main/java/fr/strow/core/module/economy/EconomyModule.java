/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:07
 */

package fr.strow.core.module.economy;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.api.properties.PropertiesCollection;
import fr.strow.core.module.economy.commands.BalTopCommand;
import fr.strow.core.module.economy.commands.CoinsCommand;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EconomyModule extends StrowModule {

    private final Injector injector;

    public EconomyModule(Injector injector) {
        super(injector.getInstance(JavaPlugin.class), injector.getInstance(CommandsManager.class), injector.getInstance(PropertiesCollection.class));

        this.injector = injector;
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Arrays.asList(
                Tuple.of("baltop", injector.getInstance(BalTopCommand.class)),
                Tuple.of("coins", injector.getInstance(CoinsCommand.class))
        );
    }

    @Override
    public List<AbstractProperty> getProperties() {
        return Collections.singletonList(
                injector.getInstance(EconomyProperty.class)
        );
    }
}