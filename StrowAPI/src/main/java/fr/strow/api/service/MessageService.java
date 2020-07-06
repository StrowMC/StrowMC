package fr.strow.api.service;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;
import java.util.function.Predicate;

public interface MessageService {

    void sendMessage(UUID uuid, BaseComponent component);

    void sendMessage(UUID uuid, String message, Object... args);

    void sendErrorMessage(UUID uuid, String message, Object... args);

    void broadcastMessage(String message, Object... args);

    void broadcastMessage(String message, Predicate<UUID> filter, Object... args);

}
