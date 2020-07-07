package fr.strow.core.module.punishment.command;


import fr.strow.core.module.punishment.command.parameter.PlayerParameter;
import fr.strow.core.module.punishment.command.parameter.ReasonParameter;
import fr.strow.core.module.punishment.command.parameter.TimeParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class TempBanCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();
    private static final Parameter<String> REASON_PARAMETER = new ReasonParameter();
    private static final Parameter<Timestamp> TIME_PARAMETER = new TimeParameter();

    public TempBanCommand(CommandDescription description) {
        super(description);
    }

    @Override
    protected void define() {
        addParam(PLAYER_PARAMETER, true);
        addParam(TIME_PARAMETER, true);
        addParam(REASON_PARAMETER, true);
    }
}