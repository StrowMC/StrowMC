package fr.strow.core.utils.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class PlayerParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;

    @Inject
    public PlayerParameter(PlayerManager playerManager) {
        super("player");

        this.playerManager = playerManager;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String arg) {
                return playerManager.playerExists(arg);
            }

            @Override
            public BaseComponent getMessage(String s) {
                return "Ce joueur n'existe pas";
            }
        });
    }

    @Override
    public StrowPlayer get(String arg) {
        return playerManager.getPlayer(arg);
    }
}
