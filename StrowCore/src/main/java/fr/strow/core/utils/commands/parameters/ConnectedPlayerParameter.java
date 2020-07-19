package fr.strow.core.utils.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class ConnectedPlayerParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;

    @Inject
    public ConnectedPlayerParameter(PlayerManager playerManager) {
        super("player");

        this.playerManager = playerManager;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String arg) {
                return playerManager.getPlayers()
                        .values()
                        .stream()
                        .anyMatch(player -> player.getProperty(Name.class).getName().equals(arg));
            }

            @Override
            public BaseComponent getMessage(String s) {
                return "Ce joueur n'est pas connecté";
            }
        });
    }

    @Override
    public StrowPlayer get(String arg) {
        return playerManager.getPlayer(arg);
    }
}
