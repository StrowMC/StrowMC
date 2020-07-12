package fr.strow.core.module.miscellaneous;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.module.miscellaneous.command.ArmorGiveCommand;
import fr.strow.core.module.miscellaneous.command.ManageExplosionsCommand;
import fr.strow.core.module.miscellaneous.listener.ArmorEquipListener;
import fr.strow.core.module.miscellaneous.listener.ExplosionListener;
import fr.strow.core.module.miscellaneous.util.ArmorRecipeRegister;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class MiscellaneousModule extends StrowModule {

    private final Injector injector;

    public MiscellaneousModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        this.injector = injector;
        ArmorRecipeRegister.register();
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
