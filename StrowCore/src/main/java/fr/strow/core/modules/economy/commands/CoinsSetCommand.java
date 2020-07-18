package fr.strow.core.modules.economy.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.core.modules.economy.commands.parameters.AmountParameter;
import fr.strow.core.utils.commands.parameters.ConnectedPlayerParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

public class CoinsSetCommand extends EvolvedCommand {

    private final CommandService commandService;

    @Inject
    public CoinsSetCommand(CommandService commandService) {
        super(CommandDescription.builder()
                .withName("set")
                .withDescription("Changer le montant du solde d'un joueur")
                .build());

        this.commandService = commandService;

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

        player
                .getProperty(Economy.class)
                .setCoins(amount);
    }
}
