package fr.strow.core.module.player;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.core.module.player.listeners.PlayerJoinListener;
import fr.strow.core.module.player.listeners.PlayerQuitListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class PlayerModule extends StrowModule {

    private final Injector injector;

    public PlayerModule(Injector injector) {
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
                new PlayerJoinListener(injector.getInstance(PlayerManager.class)),
                new PlayerQuitListener(injector.getInstance(PlayerManager.class))
        );
    }
}
