package fr.strow.core.module.punishment.command;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.player.StrowPlayerImpl;
import fr.strow.core.module.punishment.command.parameter.PlayerParameter;
import fr.strow.core.module.punishment.command.parameter.ReasonParameter;
import fr.strow.core.module.punishment.property.PunishmentProperty;
import fr.strow.core.module.punishment.utils.Punishment;
import fr.strow.core.utils.Utils;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class BanCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();
    private static final Parameter<String> REASON_PARAMETER = new ReasonParameter();

    private final PlayerManager playerManager;
    private final MessageService messageService;

    @Inject
    public BanCommand(PlayerManager playerManager, MessageService messageService) {
        super(CommandDescription.builder()
                .withPermission("strow.ban")
                .withName("ban")
                .withDescription("Permet de bannir un joueur")
                .build()
        );
        this.playerManager = playerManager;
        this.messageService = messageService;
    }

    @Override
    protected void define() {
        addParam(PLAYER_PARAMETER, true);
        addParam(REASON_PARAMETER, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        Player player = readArg();
        StrowPlayer strowPlayer = playerManager.getPlayer(player.getUniqueId());
        String reason = readArg();

        Utils.ifPresentOrElse(
                strowPlayer.getOptionalProperty(PunishmentProperty.class),
                property -> property.addPunishment(
                        new Punishment(
                                Punishment.Type.BAN,
                                Timestamp.from(Instant.now()),
                                null,
                                true,
                                ((Player) sender).getUniqueId(),
                                sender.getName(),
                                reason
                        )
                ),
                () -> {
                    ((StrowPlayerImpl) strowPlayer).registerProperty(new PunishmentProperty());
                    strowPlayer.getOptionalProperty(PunishmentProperty.class).ifPresent(property ->
                            property.addPunishment(
                                    new Punishment(
                                            Punishment.Type.BAN,
                                            Timestamp.from(Instant.now()),
                                            null,
                                            true,
                                            ((Player) sender).getUniqueId(),
                                            sender.getName(),
                                            reason
                                    )
                            )
                    );
                });
        String stringBuilder =
                "§7────────────────────────────" +
                        "§7(§3Punisher§7) §c" + player.getName() + " §e a été §6banni définitivement §epar §d" + sender.getName() + " §e!\n" +
                        "\n" +
                        " §c• §eRaison§7: §d" + reason + "\n" +
                        " §c• §eDurée§7: §aDéfinitif" + "\n" +
                        "§7────────────────────────────";
        messageService.broadcastMessage(stringBuilder, uuid -> {
            return true; //TODO rankCheck
        });
    }
}
