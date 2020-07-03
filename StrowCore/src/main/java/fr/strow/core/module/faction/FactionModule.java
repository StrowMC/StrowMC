/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core.module.faction;

import com.google.inject.Injector;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.core.module.faction.properties.FactionProfileProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactionModule extends StrowModule {

    public FactionModule(Injector injector) {
        super(injector);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return new ArrayList<>();
    }

    @Override
    public List<AbstractConfiguration> getConfigurations() {
        return new ArrayList<>();
    }

    @Override
    public List<AbstractProperty> getProperties() {
        return Arrays.asList(
                injector.getInstance(FactionProfileProperty.class)
        );
    }
}
