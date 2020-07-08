package fr.strow.core.module.punishment.listener;

import fr.strow.api.game.players.PlayerManager;
import fr.strow.core.module.punishment.property.PunishmentProperty;
import fr.strow.core.module.punishment.utils.Punishment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class ChatListener implements Listener {

    private final PlayerManager playerManager;

    public ChatListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        playerManager.getPlayer(e.getPlayer().getUniqueId()).getOptionalProperty(PunishmentProperty.class).flatMap(punishmentProperty -> punishmentProperty.getMostRecentActivePunishmentByType(Punishment.Type.MUTE)).ifPresent(punishment -> {
            e.setCancelled(true);
            LocalDateTime end = punishment.getEnd().toLocalDateTime();
            e.getPlayer().sendMessage("&cTu ne peux pas parler car tu es mute. Fin le : " + end.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " à " + end.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        });
    }

}
