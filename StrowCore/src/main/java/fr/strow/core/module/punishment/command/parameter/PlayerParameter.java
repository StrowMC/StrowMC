package fr.strow.core.module.punishment.command.parameter;

import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class PlayerParameter extends Parameter<Player> {

    public PlayerParameter() {
        super("player");
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
                return "§cJoueur introuvable";
            }
        });
    }

    @Override
    public Player get(String arg) {
        return Bukkit.getPlayer(arg);
    }
}
