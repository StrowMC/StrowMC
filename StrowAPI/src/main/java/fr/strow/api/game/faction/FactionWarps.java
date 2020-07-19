package fr.strow.api.game.faction;

import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.RegistrableProperty;
import org.bukkit.Location;

import java.util.Map;

public interface FactionWarps extends RegistrableProperty<Faction, EmptyPropertyFactory> {

    void addWarp(String name, Location location);

    void removeWarp(String name);

    boolean containsWarp(String name);

    Map<String, Location> getWarps();
}
