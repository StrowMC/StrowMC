package fr.strow.core.module.spawner;

import com.google.inject.Injector;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.core.module.spawner.property.SpawnerProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerModule extends StrowModule {

    public SpawnerModule(Injector injector) {
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
        return null;
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return null;
    }

    @Override
    public List<AbstractConfiguration> getConfigurations() {
        return null;
    }

    @Override
    public List<AbstractProperty> getProperties() {
        return Collections.singletonList(injector.getInstance(SpawnerProperty.class));
    }
}
