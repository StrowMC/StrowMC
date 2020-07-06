package fr.strow.api.service;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Created by Hokkaydo on 06-07-2020.
 */
public interface MessageService {

    void sendMessage(UUID uuid, String message, Object... args);

    void sendErrorMessage(UUID uuid, String message, Object... args);

    void broadcastMessage(String message, Object... args);

    void broadcastMessage(String message, Predicate<UUID> filter, Object... args);

}
