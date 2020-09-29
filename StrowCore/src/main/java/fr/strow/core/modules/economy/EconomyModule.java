/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:07
 */

package fr.strow.core.modules.economy;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.economy.commands.CoinsCommand;
import fr.strow.core.modules.economy.properties.EconomyProperty;
import me.choukas.commands.EvolvedCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class EconomyModule extends StrowModule {

    @Inject
    public EconomyModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        bindProperty(StrowPlayer.class, Economy.class, EconomyProperty.class);
    }

    @Override
    public List<Class<? extends EvolvedCommand>> getCommands() {
        return Collections.singletonList(
                CoinsCommand.class
        );
    }
}