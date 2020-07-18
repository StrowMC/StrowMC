package fr.strow.core.api.services;

import com.google.inject.Inject;
import fr.strow.api.game.Messenger;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class MessagingImpl implements Messaging {

    private final PlayerManager playerManager;

    @Inject
    public MessagingImpl(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void sendMessage(Messenger messenger, BaseComponent component) {
        for (UUID uuid : messenger.getRecipients()) {
            StrowPlayer player = playerManager.getPlayer(uuid);

            if (player.isConnected()) {
                Bukkit.getPlayer(uuid).spigot().sendMessage(component);
            }
        }
    }

    @Override
    public void sendMessage(Messenger messenger, String message, Object... args) {
        for (UUID uuid : messenger.getRecipients()) {
            StrowPlayer player = playerManager.getPlayer(uuid);

            if (player.isConnected()) {
                Bukkit.getPlayer(uuid).sendMessage(String.format(message, args));
            }
        }
    }

    @Override
    public void sendErrorMessage(UUID uuid, String message, Object... args) {
        Bukkit.getPlayer(uuid).sendMessage("§cUne erreur est survenue : " + String.format(message, args) + "\n§cVeuillez la signaler aux modérateurs au plus vite");
    }

    @Override
    public void broadcastMessage(String message, Object... args) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(String.format(message, args));
        }
    }

    @Override
    public void broadcastMessage(String message, Predicate<UUID> filter, Object... args) {
        for (StrowPlayer player : playerManager.getPlayers().connected().asList()) {
            if (filter.test(player.getUniqueId())) sendMessage(player, message, args);
        }
    }
}
