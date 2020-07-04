package fr.strow.core.module.punishmentmodule.command;

import fr.strow.core.module.punishmentmodule.command.parameter.PlayerParameter;
import fr.strow.core.module.punishmentmodule.command.parameter.ReasonParameter;
import fr.strow.core.module.punishmentmodule.command.parameter.TimeParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class BanCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();
    private static final Parameter<String> REASON_PARAMETER = new ReasonParameter();
    private static final Parameter<Timestamp> TIME_PARAMETER = new TimeParameter();

    public BanCommand() {
        super(CommandDescription.builder()
                .withPermission("strow.ban")
                .withName("ban")
                .withDescription("Permet de bannir un joueur")
                .build()
        );
    }

    @Override
    protected void define() {

    }
}
