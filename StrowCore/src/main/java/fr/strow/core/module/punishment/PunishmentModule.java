package fr.strow.core.module.punishment;

import com.google.inject.Injector;
import fr.strow.api.configuration.AbstractConfiguration;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.AbstractProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class PunishmentModule extends StrowModule {

    public PunishmentModule(Injector injector) {
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
        return null;
    }
}
