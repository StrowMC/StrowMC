package fr.strow.core.module.duel.command;

import fr.strow.core.module.duel.DuelModule;
import fr.strow.core.module.duel.command.parameter.DuelType;
import fr.strow.core.module.duel.command.parameter.LocationIndex;
import fr.strow.core.module.duel.util.Duel;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class SetSpawnCommand extends EvolvedCommand {

    private static final Parameter<Duel.Type> duelType = new DuelType();
    private static final Parameter<Integer> locationIndex = new LocationIndex();

    public SetSpawnCommand() {
        super(
                CommandDescription
                        .builder()
                        .withDescription("Permet de définir le spawn d'un mode")
                        .withName("setspawn")
                        .withAliases("setp")
                        .withPermission("duel.admin")
                        .build()
        );
        define();
    }

    @Override
    protected void define() {
        addParam(duelType, true);
        addParam(locationIndex, true);
    }

    @Override
    protected void execute(CommandSender sender) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez exécuter cette action en jeu");
            return;
        }

        Duel.Type type = readArg();
        int locationIndex = readArg();

        if (type == Duel.Type.ONEVONE) {
            DuelModule.setSpawnLocation(locationIndex, ((Player) sender).getLocation(), Duel.Type.TWOVTWO);
        }
        DuelModule.setSpawnLocation(locationIndex, ((Player) sender).getLocation(), type);
    }
}
