package fr.strow.api.game.faction;

import fr.strow.api.game.Property;
import org.bukkit.Location;

import java.util.Map;

public interface FactionWarps extends Property<Faction> {

    Map<String, Location> getWarps();

    void addWarp(String name, Location location);

    void removeWarp(String name);
}
