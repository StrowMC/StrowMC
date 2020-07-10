package fr.strow.core.module.duel.command;

import com.google.inject.Inject;
import fr.strow.api.StrowPlugin;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.duel.command.parameter.PlayerParameter;
import fr.strow.core.module.duel.util.DuelGroup;
import fr.strow.core.module.duel.util.DuelGroupManager;
import fr.strow.core.utils.Utils;
import me.choukas.commands.CommandCallback;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelInviteCommand extends EvolvedCommand {

    private static final Parameter<Player> playerParameter = new PlayerParameter();
    private final MessageService messageService;

    @Inject
    public DuelInviteCommand(MessageService messageService) {
        super(
                CommandDescription
                        .builder()
                        .withDescription("Permet d'inviter quelqu'un dans son groupe")
                        .withName("invite")
                        .build()
        );
        define();
        this.messageService = messageService;
    }

    @Override
    protected void define() {
        addParam(playerParameter, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez exécuter cette action en jeu");
            return;
        }
        Player target = readArg();

        Player player = (Player) sender;

        Utils.ifPresentOrElse(
                DuelGroupManager.getPlayerGroup(player),
                g -> {
                    if (!g.getLeader().equals(player.getUniqueId())) {
                        messageService.sendMessage(player.getUniqueId(), "§cVous n'êtes pas le chef de votre groupe");
                        return;
                    }
                    if (DuelGroupManager.getPlayerGroup(target).isPresent()) {
                        messageService.sendMessage(player.getUniqueId(), "§cCe joueur est déjà dans un groupe");
                        return;
                    }
                    invitePlayer(player, target, g);
                },
                () -> {
                    DuelGroup g = new DuelGroup(player.getUniqueId());
                    invitePlayer(player, target, g);
                    DuelGroupManager.addGroup(g);
                });

    }

    private void invitePlayer(Player leader, Player target, DuelGroup g) {
        AtomicBoolean tooLate = new AtomicBoolean(false);
        messageService.sendMessage(leader.getUniqueId(), "§aCe joueur a été invité dans votre groupe. Il a 1 minute pour accepter");
        CommandCallback.createCommand(
                new TextComponent(String.format("§a%s vous a invité dans ce groupe. Veuillez cliquer sur ce message pour accepter sa demande",
                        leader.getName())),
                target.getUniqueId(), () -> {
                    if (!tooLate.get()) {
                        g.addPlayer(target.getUniqueId());
                        g.getPlayers().forEach(uuid -> messageService.sendMessage(uuid, "§a%s a rejoint le groupe", target.getName()));
                        messageService.sendMessage(target.getUniqueId(), "§aVous avez rejoint le groupe de %s", leader.getName());
                    }
                });
        new BukkitRunnable() {
            @Override
            public void run() {
                tooLate.set(true);
            }
        }.runTaskLater(JavaPlugin.getPlugin(StrowPlugin.class), 20 * 60);
    }
}
