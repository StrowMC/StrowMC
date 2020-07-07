package fr.strow.core.module.punishment.command;


import fr.strow.core.module.punishment.command.parameter.PlayerParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class ListSanctionCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();

    public ListSanctionCommand(CommandDescription description) {
        super(description);
    }

    @Override
    protected void define() {
        addParam(PLAYER_PARAMETER, true);
    }
}
