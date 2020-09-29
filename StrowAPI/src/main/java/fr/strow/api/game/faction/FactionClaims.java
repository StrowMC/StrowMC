package fr.strow.api.game.faction;

import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.RegistrableProperty;
import org.bukkit.Chunk;

public interface FactionClaims extends RegistrableProperty<Faction, EmptyPropertyFactory> {

    void addClaim(Chunk claim);

    void removeClaim(Chunk claim);

    boolean containsClaim(Chunk claim);
}
