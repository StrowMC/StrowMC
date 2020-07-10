package fr.strow.core.utils.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class PlayerParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;

    @Inject
    public PlayerParameter(PlayerManager playerManager) {
        super("player");

        this.playerManager = playerManager;
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String o) {
                return Bukkit.getPlayer(o) != null && Bukkit.getPlayer(o).isOnline();
            }

            @Override
            public String getMessage(String s) {
                return "Joueur introuvable";
            }
        });
    }

    @Override
    public StrowPlayer get(String arg) {
        return playerManager.getPlayer(Bukkit.getPlayer(arg).getUniqueId());
    }
}
