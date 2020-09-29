package fr.strow.api.game;

import java.util.Collection;
import java.util.UUID;

public interface Messenger {

    Collection<UUID> getRecipients();
}
