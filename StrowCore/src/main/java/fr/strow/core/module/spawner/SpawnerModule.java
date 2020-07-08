package fr.strow.core.module.spawner;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.Property;
import fr.strow.core.module.spawner.command.SpawnerCommand;
import fr.strow.core.module.spawner.command.SpawnerGiveCommand;
import fr.strow.core.module.spawner.listener.InventoryClickListener;
import fr.strow.core.module.spawner.property.SpawnerProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerModule extends StrowModule {

    private final Injector injector;

    public SpawnerModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        this.injector = injector;
    }

    @Override
    public List<Listener> getListeners() {
        return Collections.singletonList(injector.getInstance(InventoryClickListener.class));
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Arrays.asList(
                Tuple.of("spawnergive", injector.getInstance(SpawnerGiveCommand.class)),
                Tuple.of("spawner", injector.getInstance(SpawnerCommand.class))
        );
    }

    @Override
    public List<Class<? extends Property>> getProperties() {
        return Collections.singletonList(SpawnerProperty.class);
    }
}
