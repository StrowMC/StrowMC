package fr.strow.api.services;

import fr.strow.api.game.Messenger;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;
import java.util.function.Predicate;

public interface Messaging {

    void sendMessage(Messenger messenger, BaseComponent component);

    void sendMessage(Messenger messenger, String message, Object... args);

    void sendErrorMessage(UUID uuid, String message, Object... args);

    void broadcastMessage(String message, Object... args);

    void broadcastMessage(String message, Predicate<UUID> filter, Object... args);

}
