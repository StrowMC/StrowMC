package fr.strow.api.game.faction;

import fr.strow.api.game.StrowCollection;
import org.bukkit.Chunk;

import java.util.List;
import java.util.UUID;

public class FactionsCollection extends StrowCollection<Faction> {

    public FactionsCollection(List<Faction> factions) {
        super(factions);
    }

    public Filter<Faction> withUniqueId(UUID uuid) {
        return filter().add(faction ->
                faction.getUniqueId().equals(uuid));
    }

    public Filter<Faction> withName(String name) {
        return filter().add(faction ->
                faction.getProperty(FactionName.class).equals(name));
    }

    public Filter<Faction> withClaim(Chunk claim) {
        return filter().add(faction ->
                faction.getProperty(FactionClaims.class).contains(claim));
    }
}
