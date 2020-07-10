package fr.strow.core.module.punishment.command;

import com.google.inject.Inject;
import fr.strow.api.commands.ParametersCollection;
import fr.strow.api.game.players.Pseudo;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.player.StrowPlayerImpl;
import fr.strow.core.module.punishment.command.parameter.ReasonParameter;
import fr.strow.core.module.punishment.property.BanImplementationProperty;
import fr.strow.core.utils.Utils;
import fr.strow.core.utils.commands.parameters.PlayerParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class BanCommand extends EvolvedCommand {

    private final ParametersCollection parameters;
    private final PropertiesHandler propertiesHandler;
    private final MessageService messageService;

    @Inject
    public BanCommand(ParametersCollection parameters, PropertiesHandler propertiesHandler, MessageService messageService) {
        super(CommandDescription.builder()
                .withPermission("strow.ban")
                .withName("ban")
                .withDescription("Permet de bannir un joueur")
                .build()
        );

        this.parameters = parameters;
        this.propertiesHandler = propertiesHandler;
        this.messageService = messageService;

        define();
    }

    @Override
    protected void define() {
        addParam(parameters.getParameter(PlayerParameter.class), true);
        addParam(parameters.getParameter(ReasonParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer player = readArg();
        String reason = readArg();

        Utils.ifPresentOrElse(
                player.getOptionalProperty(BanImplementationProperty.class),
                property -> property.add(
                        new BanImplementationProperty.Ban(
                                reason,
                                ((Player) sender).getUniqueId(),
                                Timestamp.from(Instant.now()),
                                Timestamp.from(Instant.MAX)
                        )
                ),
                () -> {
                    ((StrowPlayerImpl) player).registerProperty(propertiesHandler.getProperty(BanImplementationProperty.class));
                    player.getProperty(BanImplementationProperty.class).add(
                            new BanImplementationProperty.Ban(
                                    reason,
                                    ((Player) sender).getUniqueId(),
                                    Timestamp.from(Instant.now()),
                                    Timestamp.from(Instant.MAX)
                            )
                    );
                });

        String stringBuilder =
                "§7────────────────────────────" +
                        "§7(§3Punisher§7) §c" + player.getProperty(Pseudo.class) + " §e a été §6banni définitivement §epar §d" + sender.getName() + " §e!\n" +
                        "\n" +
                        " §c• §eRaison§7: §d" + reason + "\n" +
                        " §c• §eDurée§7: §aDéfinitif" + "\n" +
                        "§7────────────────────────────";

        messageService.broadcastMessage(stringBuilder, uuid -> {
            return true; //TODO rankCheck
        });
    }
}
