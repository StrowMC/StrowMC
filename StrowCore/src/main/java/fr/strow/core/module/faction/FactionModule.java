/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core.module.faction;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.ImplementationProperty;
import fr.strow.core.module.faction.commands.FactionCommand;
import fr.strow.core.module.faction.commands.FactionCreateCommand;
import fr.strow.core.module.faction.properties.player.profile.FactionProfileImplementationProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FactionModule extends StrowModule {

    private final Injector injector;

    @Inject
    public FactionModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );

        this.injector = injector;
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Arrays.asList(
                Tuple.of("faction", injector.getInstance(FactionCommand.class)),
                Tuple.of("factionCreate", injector.getInstance(FactionCreateCommand.class))
        );
    }

    @Override
    public List<Class<? extends ImplementationProperty>> getProperties() {
        return Collections.singletonList(
                FactionProfileImplementationProperty.class
        );
    }
}
