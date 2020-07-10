package fr.strow.core.module.duel.command;

import com.google.inject.Inject;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.duel.util.DuelGroupManager;
import fr.strow.core.module.duel.util.DuelManager;
import fr.strow.core.utils.Utils;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelLeaveCommand extends EvolvedCommand {

    private final MessageService messageService;

    @Inject
    public DuelLeaveCommand(MessageService messageService) {
        super(
                CommandDescription
                        .builder()
                        .withName("leave")
                        .withDescription("Permet de quitter un duel")
                        .build()
        );
        this.messageService = messageService;
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez exécuter cette action en jeu");
            return;
        }
        Player player = (Player) sender;

        Utils.ifPresentOrElse(
                DuelGroupManager.getPlayerGroup(player),
                g -> {
                    if (!g.getLeader().equals(player.getUniqueId())) {
                        messageService.sendMessage(player.getUniqueId(), "§cVous n'êtes pas le chef de votre groupe");
                        return;
                    }
                    if (!DuelManager.isInDuel(g)) {
                        messageService.sendMessage(player.getUniqueId(), "§cVous n'êtes pas en duel");
                        return;
                    }
                    DuelManager.removeDuel(g);
                    messageService.sendMessage(player.getUniqueId(), "§aVous n'êtes plus en duel");
                },
                () -> {
                    if (!DuelManager.isInDuel(player)) {
                        messageService.sendMessage(player.getUniqueId(), "§cVous n'êtes pas en duel");
                        return;
                    }
                    DuelManager.getDuel(player).ifPresent(DuelManager::removeDuel);
                    messageService.sendMessage(player.getUniqueId(), "§aVous n'êtes plus en duel");
                });
    }
}
