package fr.strow.core.utils.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
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
            public boolean check(String o) {
                return playerManager.getPlayers()
                        .connected()
                        .withPseudo(o)
                        .findAny();
            }

            @Override
            public String getMessage(String s) {
                return "Joueur introuvable";
            }
        });
    }

    @Override
    public StrowPlayer get(String arg) {
        return playerManager.getPlayer(arg);
    }
}
