package fr.strow.core.module.spawner.property;

import fr.strow.api.properties.ImplicitInitialisedProperty;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.Property;
import fr.strow.core.module.spawner.Spawner;
import org.bukkit.Location;

import java.util.*;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerProperty implements Property, ImplicitInitialisedProperty, PersistentProperty {

    private final Map<Spawner, Location> activeSpawners = new HashMap<>();
    private final List<Spawner> inactiveSpawners = new ArrayList<>();

    public List<Spawner> getActiveSpawners() {
        return new ArrayList<>(activeSpawners.keySet());
    }

    public List<Spawner> getInactiveSpawners() {
        return new ArrayList<>(inactiveSpawners);
    }

    public boolean addSpawner(Spawner spawner) {
        if (inactiveSpawners.contains(spawner) || activeSpawners.containsKey(spawner)) return false;
        inactiveSpawners.add(spawner);
        return true;
    }

    public boolean removeSpawner(Spawner spawner) {
        if (!inactiveSpawners.contains(spawner)) return false;
        inactiveSpawners.remove(spawner);
        return true;
    }

    public boolean activateSpawner(Spawner spawner, Location location) {
        if (activeSpawners.containsKey(spawner) || !inactiveSpawners.contains(spawner)) return false;
        inactiveSpawners.remove(spawner);
        activeSpawners.put(spawner, location);
        return true;
    }

    public boolean inactivateSpawner(Spawner spawner) {
        if (inactiveSpawners.contains(spawner) || !activeSpawners.containsKey(spawner)) return false;
        activeSpawners.remove(spawner);
        inactiveSpawners.add(spawner);
        return true;
    }

    @Override
    public void load(UUID uuid) {

    }

    @Override
    public void save(UUID uuid) {

    }
}
