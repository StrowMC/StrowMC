package fr.strow.core.module.spawner.command.parameter;

import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class PlayerParameter extends Parameter<Player> {

    public PlayerParameter() {
        super("player");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String s) {
                        return Bukkit.getPlayer(s) != null && Bukkit.getPlayer(s).isOnline();
                    }

                    @Override
                    public String getMessage(String s) {
                        return "§cJoueur introuvable";
                    }
                }
        );
    }

    @Override
    public Player get(String arg) {
        return Bukkit.getPlayer(arg);
    }
}