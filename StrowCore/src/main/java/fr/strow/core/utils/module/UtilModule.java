package fr.strow.core.utils.module;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.utils.event.armorequipevent.ArmorListener;
import fr.strow.core.utils.event.armorequipevent.DispenserArmorListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class UtilModule extends StrowModule {

    private final Injector injector;

    public UtilModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        this.injector = injector;
    }

    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(
                injector.getInstance(ArmorListener.class),
                injector.getInstance(DispenserArmorListener.class)
        );
    }
}
