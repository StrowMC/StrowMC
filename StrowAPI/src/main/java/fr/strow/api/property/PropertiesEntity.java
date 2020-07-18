package fr.strow.api.property;

import fr.strow.api.game.Property;

import java.util.Collection;
import java.util.UUID;

public abstract class PropertiesEntity<O extends PropertiesOwner<O>> extends PropertiesGrouping<O> {

    private final Class<O> owner;

    public PropertiesEntity(Class<O> owner, PropertiesHandler propertiesHandler) {
        super(owner, propertiesHandler);

        this.owner = owner;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    public void registerProperty(Class<? extends Property<O>> property) {
        properties.put(property, propertiesHandler.getProperty(owner, property));
    }

    public void unregisterProperty(Class<? extends Property<O>> property) {
        properties.remove(property);
    }

    public Collection<ImplementationProperty> getProperties() {
        return properties.values();
    }
}
