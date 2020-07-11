package fr.strow.core.module.miscelaneous;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.module.miscelaneous.command.ArmorGiveCommand;
import fr.strow.core.module.miscelaneous.command.ManageExplosionsCommand;
import fr.strow.core.module.miscelaneous.listener.ArmorEquipListener;
import fr.strow.core.module.miscelaneous.listener.ExplosionListener;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class MiscelaneousModule extends StrowModule {

    private final Injector injector;

    public MiscelaneousModule(Injector injector) {
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
                Tuple.of("explosions", injector.getInstance(ManageExplosionsCommand.class)),
                Tuple.of("armorgive", injector.getInstance(ArmorGiveCommand.class))
        );
    }

    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(
                injector.getInstance(ExplosionListener.class),
                injector.getInstance(ArmorEquipListener.class)
        );
    }
}
