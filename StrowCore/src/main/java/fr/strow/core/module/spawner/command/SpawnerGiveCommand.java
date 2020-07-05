package fr.strow.core.module.spawner.command;

import fr.strow.core.module.spawner.Spawner;
import fr.strow.core.module.spawner.command.parameter.PlayerParameter;
import fr.strow.core.module.spawner.command.parameter.SpawnerParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerGiveCommand extends EvolvedCommand {

    private static final Parameter<Spawner> SPAWNER_PARAMETER = new SpawnerParameter();
    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();

    public SpawnerGiveCommand() {
        super(CommandDescription.builder()
                .withName("spawnergive")
                .withConsoleExecutable(true)
                .withPermission("*")
                .withDescription("Permet d'ajouter un spawner dans l'inventaire d'un joueur")
                .build());
    }


    @Override
    protected void define() {
        addParam(SPAWNER_PARAMETER, true);
        addParam(PLAYER_PARAMETER, false);
    }

    @Override
    protected void execute(CommandSender sender) {
        super.execute(sender);
    }
}
