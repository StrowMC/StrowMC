package fr.strow.core.module.moderation;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.module.moderation.command.ModCommand;
import fr.strow.core.module.moderation.listener.JoinListener;
import fr.strow.core.module.moderation.listener.ModListener;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class ModerationModule extends StrowModule {

    public static final String PREFIX = "§f[§c!§f]";
    private static final List<UUID> modPlayers = new ArrayList<>();
    private static final List<UUID> freezedPlayers = new ArrayList<>();
    private final Injector injector;

    public ModerationModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        this.injector = injector;
    }

    public static boolean isInModMode(UUID uuid) {
        return modPlayers.contains(uuid);
    }

    public static void addMod(UUID uuid) {
        modPlayers.add(uuid);
    }

    public static void removeMod(UUID uuid) {
        modPlayers.remove(uuid);
    }

    public static boolean isFreezed(UUID uuid) {
        return freezedPlayers.contains(uuid);
    }

    public static void freeze(UUID uuid) {
        freezedPlayers.add(uuid);
    }

    public static void unfreeze(UUID uuid) {
        freezedPlayers.remove(uuid);
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Collections.singletonList(Tuple.of("mod", injector.getInstance(ModCommand.class)));
    }

    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(
                injector.getInstance(JoinListener.class),
                injector.getInstance(ModListener.class)
        );
    }
}
