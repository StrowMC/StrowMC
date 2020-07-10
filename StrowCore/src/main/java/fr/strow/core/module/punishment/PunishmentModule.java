package fr.strow.core.module.punishment;

import com.google.inject.Injector;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.modules.StrowModule;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.ImplementationProperty;
import fr.strow.core.module.punishment.command.*;
import fr.strow.core.module.punishment.listener.ChatListener;
import fr.strow.core.module.punishment.property.PunishmentProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class PunishmentModule extends StrowModule {

    private final Injector injector;

    public PunishmentModule(Injector injector) {
        super(
                injector.getInstance(JavaPlugin.class),
                injector.getInstance(CommandsManager.class),
                injector.getInstance(PropertiesHandler.class)
        );
        this.injector = injector;
    }

    @Override
    public void onEnable() {
        //TODO set value of PUNISHMENT_ID from db
    }

    @Override
    public void onDisable() {
        //TODO save PUNISHMENT_ID to db
    }

    @Override
    public List<Class<? extends ImplementationProperty>> getProperties() {
        return Collections.singletonList(PunishmentProperty.class);
    }

    @Override
    public List<Tuple<String, EvolvedCommand>> getCommands() {
        return Arrays.asList(
                Tuple.of("ban", injector.getInstance(BanCommand.class)),
                Tuple.of("listsanction", injector.getInstance(ListSanctionCommand.class)),
                Tuple.of("kick", injector.getInstance(KickCommand.class)),
                Tuple.of("tempban", injector.getInstance(TempBanCommand.class)),
                Tuple.of("tempmute", injector.getInstance(TempMuteCommand.class))
        );
    }

    @Override
    public List<Listener> getListeners() {
        return Collections.singletonList(
                new ChatListener(injector.getInstance(PlayerManager.class))
        );
    }
}
