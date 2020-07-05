package fr.strow.core.module.spawner;

import org.bukkit.entity.EntityType;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public enum Spawner {
    ;
    private final EntityType entityType;

    Spawner(EntityType entityType){
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
