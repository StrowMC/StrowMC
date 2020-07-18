package fr.strow.api.game.faction;

import fr.strow.api.game.Property;
import org.bukkit.Chunk;

import java.util.List;

public interface FactionClaims extends Property<Faction> {

    List<Chunk> getClaims();

    boolean contains(Chunk chunk);
}
