package fr.strow.core.module.punishment.command;


import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.GameUtilsService;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.player.StrowPlayerImpl;
import fr.strow.core.module.punishment.command.parameter.PlayerParameter;
import fr.strow.core.module.punishment.command.parameter.ReasonParameter;
import fr.strow.core.module.punishment.command.parameter.TimeParameter;
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
public class TempBanCommand extends EvolvedCommand {

    private static final Parameter<Player> PLAYER_PARAMETER = new PlayerParameter();
    private static final Parameter<String> REASON_PARAMETER = new ReasonParameter();
    private static final Parameter<Timestamp> TIME_PARAMETER = new TimeParameter();

    private final PlayerManager playerManager;
    private final MessageService messageService;
    private final GameUtilsService gameUtilsService;

    public TempBanCommand(PlayerManager playerManager, MessageService messageService, GameUtilsService gameUtilsService) {
        super(CommandDescription
                .builder()
                .withName("tempban")
                .withDescription("Permet de bannir temporairement un joueur")
                .withPermission("strow.tempban")
                .build()
        );
        this.playerManager = playerManager;
        this.messageService = messageService;
        this.gameUtilsService = gameUtilsService;
    }

    @Override
    protected void define() {
        addParam(PLAYER_PARAMETER, true);
        addParam(TIME_PARAMETER, true);
        addParam(REASON_PARAMETER, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        Player target = readArg();
        Timestamp end = readArg();
        String reason = readArg();

        StrowPlayer strowPlayer = playerManager.getPlayer(target.getUniqueId());

        Utils.ifPresentOrElse(
                strowPlayer.getOptionalProperty(PunishmentProperty.class),
                property -> property.addPunishment(
                        new Punishment(
                                Punishment.Type.BAN,
                                Timestamp.from(Instant.now()),
                                end,
                                false,
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
                                            end,
                                            false,
                                            ((Player) sender).getUniqueId(),
                                            sender.getName(),
                                            reason
                                    )
                            )
                    );
                });
        String stringBuilder =
                "§7────────────────────────────" +
                        "§7(§3Punisher§7) §c" + target.getName() + " §e a été rendu §6temporairement muet §epar §d" + sender.getName() + " §e!\n" +
                        "\n" +
                        " §c• §eRaison§7: §d" + reason + "\n" +
                        " §c• §eDurée§7: §a" + gameUtilsService.formatTime(Instant.ofEpochSecond(end.toInstant().getEpochSecond() - Instant.now().getEpochSecond())) + "\n" +
                        "§7────────────────────────────";
        messageService.broadcastMessage(stringBuilder, uuid -> {
            return true; //TODO rankCheck
        });
    }
}
