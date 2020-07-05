package fr.strow.core.module.spawner.property;

import fr.strow.api.properties.AbstractProperty;
import fr.strow.core.module.spawner.Spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerProperty implements AbstractProperty {

    private final List<Spawner> spawners = new ArrayList<>();

    public List<Spawner> getSpawners(){
        return spawners;
    }

    @Override
    public void load(UUID uuid) {

    }

    @Override
    public void save(UUID uuid) {

    }
}
