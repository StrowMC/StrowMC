package fr.strow.core.modules.moderation.listener;

import com.google.inject.Inject;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.moderation.ModerationModule;
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

    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public JoinListener(PlayerManager playerManager, Messaging messaging) {
        this.playerManager = playerManager;
        this.messaging = messaging;
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
            messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s §cTu n'est plus en mode staff.", ModerationModule.PREFIX);
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
