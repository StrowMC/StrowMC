package fr.strow.api.property;

import fr.strow.api.game.Property;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class PropertiesGrouping<O extends PropertiesOwner<O>> extends ImplementationProperty implements PropertiesOwner<O> {

    protected final Class<O> owner;
    protected final PropertiesHandler propertiesHandler;
    protected Map<Class<? extends Property<O>>, ImplementationProperty> properties;

    protected UUID uuid;

    public PropertiesGrouping(Class<O> owner, PropertiesHandler propertiesHandler) {
        this.owner = owner;
        this.propertiesHandler = propertiesHandler;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        properties = propertiesHandler.getProperties(owner, uuid);

        return true;
    }

    @Override
    public void save(UUID uuid) {
        for (ImplementationProperty property : properties.values()) {
            property.save(uuid);
        }
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends Property<O>> P getProperty(Class<P> property) {
        return (P) properties.get(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends Property<O>> Optional<P> getOptionalProperty(Class<P> property) {
        return Optional.ofNullable((P) properties.get(property));
    }
}
