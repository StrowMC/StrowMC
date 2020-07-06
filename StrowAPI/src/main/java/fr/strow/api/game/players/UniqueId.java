package fr.strow.api.game.players;

import fr.strow.api.game.AbstractProperty;

import java.util.UUID;

public interface UniqueId extends AbstractProperty {

    UUID getUniqueId();
}
