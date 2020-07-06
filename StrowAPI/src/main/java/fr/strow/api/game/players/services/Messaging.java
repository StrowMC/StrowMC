package fr.strow.api.game.players.services;

import fr.strow.api.game.AbstractService;
import net.md_5.bungee.api.chat.BaseComponent;

public interface Messaging extends AbstractService {

    void sendMessage(String message);

    void sendMessage(String format, Object... args);

    void sendMessage(BaseComponent component);
}
