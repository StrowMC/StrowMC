package fr.strow.core.quest.command;

import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.service.MessageService;
import fr.strow.core.quest.QuestRepository;
import fr.strow.core.quest.property.QuestProperty;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class QuestCommand extends EvolvedCommand {

    private final MessageService messageService;
    private final PlayerManager playerManager;

    public QuestCommand(PlayerManager playerManager, MessageService messageService) {
        super(
                CommandDescription
                        .builder()
                        .withName("quest")
                        .withAliases("quetes")
                        .withDescription("Permet de consulter la quête en cours")
                        .build()
        );
        this.playerManager = playerManager;
        this.messageService = messageService;
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez exécuter cette action en jeu");
            return;
        }
        playerManager.getPlayer(((Player) sender).getUniqueId())
                .getOptionalProperty(QuestProperty.class)
                .ifPresent(property ->
                        QuestRepository.getById(property.getQuestId()).ifPresent(q ->
                                messageService.sendMessage(
                                        ((Player) sender).getUniqueId(),
                                        "§aQuête %d en cours : %s %s/%s",
                                        q.getId(),
                                        q.getName(),
                                        property.getCurrent(),
                                        q.getObjective()
                                )
                        )
                );
    }
}
