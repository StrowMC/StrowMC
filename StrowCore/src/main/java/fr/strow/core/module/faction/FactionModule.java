/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core.module.faction;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.PropertiesCollection;
import fr.strow.core.module.faction.commands.FactionCommand;
import fr.strow.core.module.faction.commands.FactionCreateCommand;
import fr.strow.core.module.faction.properties.FactionProfileProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FactionModule extends StrowModule {

    private final Injector injector;

    public FactionModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesCollection.class)
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
    public List<PersistentProperty> getProperties() {
        return Collections.singletonList(
                injector.getInstance(FactionProfileProperty.class)
        );
    }
}
