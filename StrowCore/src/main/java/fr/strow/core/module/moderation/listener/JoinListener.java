package fr.strow.core.module.moderation.listener;

import com.google.inject.Inject;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.moderation.ModerationModule;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class JoinListener implements Listener {

    private final MessageService messageService;

    @Inject
    public JoinListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (ModerationModule.isInModMode(e.getPlayer().getUniqueId())) {
            e.getPlayer().getActivePotionEffects().forEach(p -> e.getPlayer().removePotionEffect(p.getType()));
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            Bukkit.getOnlinePlayers().forEach(p -> {
                e.getPlayer().showPlayer(p);
                p.showPlayer(e.getPlayer());
            });
            messageService.sendMessage(e.getPlayer().getUniqueId(), "%s §cTu n'est plus en mode staff.", ModerationModule.PREFIX);
            e.getPlayer().getInventory().clear();
            e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
        }

        for (Player p1 : Bukkit.getOnlinePlayers()) {
            if (!ModerationModule.isInModMode(p1.getUniqueId())) {
                e.getPlayer().showPlayer(p1);
            }
        }
    }
}
