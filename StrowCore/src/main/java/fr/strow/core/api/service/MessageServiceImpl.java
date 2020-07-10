package fr.strow.core.api.service;

import fr.strow.api.service.MessageService;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class MessageServiceImpl implements MessageService {

    @Override
    public void sendMessage(UUID uuid, BaseComponent component) {
        Bukkit.getPlayer(uuid).spigot().sendMessage(component);
    }

    @Override
    public void sendMessage(UUID uuid, String message, Object... args) {
        Bukkit.getPlayer(uuid).sendMessage(String.format(message, args));
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
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (filter.test(player.getUniqueId())) sendMessage(player.getUniqueId(), message, args);
        }
    }
}
