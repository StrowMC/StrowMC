package fr.strow.core.api.services;

import com.google.inject.Inject;
import fr.strow.api.game.Messenger;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            if (playerManager.isConnected(uuid)) {
                StrowPlayer player = playerManager.getPlayer(uuid);

                Bukkit.getPlayer(player.getProperty(Name.class).getName()).spigot().sendMessage(component);
            }
        }
    }

    @Override
    public void sendMessage(Messenger messenger, Predicate<UUID> filter, BaseComponent component) {
        for (UUID uuid : messenger.getRecipients()) {
            if (playerManager.isConnected(uuid) && filter.test(uuid)) {
                StrowPlayer player = playerManager.getPlayer(uuid);

                Bukkit.getPlayer(player.getProperty(Name.class).getName()).spigot().sendMessage(component);
            }
        }
    }

    @Override
    public void sendMessage(Messenger messenger, String message, Object... args) {
        for (UUID uuid : messenger.getRecipients()) {
            if (playerManager.isConnected(uuid)) {
                StrowPlayer player = playerManager.getPlayer(uuid);

                Bukkit.getPlayer(player.getProperty(Name.class).getName()).sendMessage(String.format(message, args));
            }
        }
    }

    @Override
    public void sendMessage(Messenger messenger, Predicate<UUID> filter, String message, Object... args) {
        for (UUID uuid : messenger.getRecipients()) {
            if (playerManager.isConnected(uuid) && filter.test(uuid)) {
                StrowPlayer player = playerManager.getPlayer(uuid);

                Bukkit.getPlayer(player.getProperty(Name.class).getName()).sendMessage(String.format(message, args));
            }
        }
    }

    @Override
    public BaseComponent[] errorMessage(String message) {
        return TextComponent.fromLegacyText(ChatColor.RED + message);
    }

    @Override
    public BaseComponent[] errorMessage(String message, Object... args) {
        return errorMessage(String.format(message, args));
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
        for (StrowPlayer player : playerManager.getPlayers().values()) {
            if (filter.test(player.getUniqueId())) sendMessage(player, message, args);
        }
    }
}
