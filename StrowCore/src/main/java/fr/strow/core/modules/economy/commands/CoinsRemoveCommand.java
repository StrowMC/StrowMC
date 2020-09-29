package fr.strow.core.modules.economy.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.economy.commands.parameters.AmountParameter;
import fr.strow.core.utils.commands.parameters.ConnectedPlayerParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

public class CoinsRemoveCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final Messaging messaging;

    @Inject
    public CoinsRemoveCommand(CommandService commandService, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("remove")
                .withDescription("Retirer de l'argent au solde d'un joueur")
                .build());

        this.commandService = commandService;
        this.messaging = messaging;

        define();
    }

    @Override
    protected void define() {
        addParameter(commandService.getParameter(ConnectedPlayerParameter.class), true);
        addParameter(commandService.getParameter(AmountParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer player = readArg();
        int amount = readArg();

        player.getProperty(Economy.class).removeCoins(amount);
        messaging.sendMessage(player, "Votre compte a été débité de %d$", amount);
    }
}
