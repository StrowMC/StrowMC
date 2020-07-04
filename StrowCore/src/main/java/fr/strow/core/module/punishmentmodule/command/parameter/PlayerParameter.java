package fr.strow.core.module.punishmentmodule.command.parameter;

import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class PlayerParameter extends Parameter<Player> {

    public PlayerParameter() {
        super("player");
    }

    @Override
    public Optional<Player> check(String s) {
        return Bukkit.getPlayer(s) == null || !Bukkit.getPlayer(s).isOnline() ? Optional.empty() : Optional.of(Bukkit.getPlayer(s));
    }

    @Override
    public String getMessage(String s) {
        return "§cJoueur introuvable";
    }
}
