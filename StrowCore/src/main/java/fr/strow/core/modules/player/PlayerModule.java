package fr.strow.core.modules.player;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.module.StrowModule;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.player.listeners.PlayerJoinListener;
import fr.strow.core.modules.player.listeners.PlayerQuitListener;
import fr.strow.core.modules.player.properties.PseudoProperty;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class PlayerModule extends StrowModule {

    private final Injector injector;

    @Inject
    public PlayerModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandService.class),
                injector.getInstance(PropertiesHandler.class));

        this.injector = injector;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        bindProperty(StrowPlayer.class, Pseudo.class, PseudoProperty.class);
    }

    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(
                injector.getInstance(PlayerJoinListener.class),
                injector.getInstance(PlayerQuitListener.class)
        );
    }
}
