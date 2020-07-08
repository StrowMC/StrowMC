package fr.strow.core.module.spawner;

import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public enum Spawner {
    ;
    private final String name;
    private final EntityType entityType;

    Spawner(String name, EntityType entityType) {
        this.name = name;
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static Optional<Spawner> getByName(String name) {
        return Arrays.stream(values()).filter(s -> s.name.equalsIgnoreCase(name)).findFirst();
    }

    public String getName() {
        return name;
    }
}
