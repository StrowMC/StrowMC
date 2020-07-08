package fr.strow.core.module.spawner.command;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.player.StrowPlayerImpl;
import fr.strow.core.module.spawner.Spawner;
import fr.strow.core.module.spawner.command.parameter.PlayerParameter;
import fr.strow.core.module.spawner.command.parameter.SpawnerParameter;
import fr.strow.core.module.spawner.property.SpawnerProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerGiveCommand extends EvolvedCommand {

    private static final Parameter<Spawner> SPAWNER_PARAMETER = new SpawnerParameter();
    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();

    private final PlayerManager playerManager;
    private final MessageService messageService;

    @Inject
    public SpawnerGiveCommand(PlayerManager playerManager, MessageService messageService) {
        super(CommandDescription.builder()
                .withName("spawnergive")
                .withPermission("*")
                .withDescription("Permet d'ajouter un spawner dans l'inventaire d'un joueur")
                .build()
        );
        this.playerManager = playerManager;
        this.messageService = messageService;
        define();
    }


    @Override
    protected void define() {
        addParam(SPAWNER_PARAMETER, true);
        addParam(PLAYER_PARAMETER, false);
    }

    @Override
    protected void execute(CommandSender sender) {
        Spawner spawner = readArg();
        Optional<Player> player = readOptionalArg();

        if (!player.isPresent() && sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez préciser un joueur");
            return;
        }

        StrowPlayer strowPlayer = playerManager.getPlayer(player.orElse((Player) sender).getUniqueId());

        boolean success = strowPlayer.getOptionalProperty(SpawnerProperty.class).orElseGet(() -> {
            SpawnerProperty spawnerProperty = new SpawnerProperty();
            ((StrowPlayerImpl) strowPlayer).registerProperty(spawnerProperty);
            return spawnerProperty;
        }).addSpawner(spawner);

        if (success) {
            sender.sendMessage("§aSpawner ajouté");
            messageService.sendMessage(strowPlayer.getUniqueId(), "§aVous avez reçu le spawner %s !", spawner.getName());
        } else {
            sender.sendMessage("§cLe joueur possède déjà ce spawner");
        }
    }
}
