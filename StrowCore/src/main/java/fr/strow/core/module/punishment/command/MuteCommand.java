package fr.strow.core.module.punishment.command;


import fr.strow.core.module.punishment.command.parameter.PlayerParameter;
import fr.strow.core.module.punishment.command.parameter.ReasonParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class MuteCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();
    private static final Parameter<String> REASON_PARAMETER = new ReasonParameter();

    public MuteCommand(CommandDescription description) {
        super(description);
    }

    @Override
    protected void define() {
        addParam(PLAYER_PARAMETER, true);
        addParam(REASON_PARAMETER, true);
    }
}
