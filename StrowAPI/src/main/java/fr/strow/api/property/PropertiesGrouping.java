package fr.strow.api.property;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class PropertiesGrouping<O extends PropertiesOwner<O>> implements PropertiesOwner<O>, Registerer<O> {

    protected UUID uuid;

    private final Class<O> owner;
    private final PropertiesHandler propertiesHandler;
    private Map<Class<? extends Property<O>>, ImplementationProperty<? extends Property<O>>> properties;

    public PropertiesGrouping(Class<O> owner, PropertiesHandler propertiesHandler) {
        this.owner = owner;
        this.propertiesHandler = propertiesHandler;
    }

    public boolean load(UUID uuid) {
        this.uuid = uuid;

        properties = propertiesHandler.getProperties(owner, uuid);

        return true;
    }

    public void save(UUID uuid) {
        for (ImplementationProperty<? extends Property<O>> property : properties.values()) {
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
        return Optional.ofNullable((P) properties.get(property)).orElseThrow(() -> new IllegalArgumentException("This owner doesn't own the property or it hasn't been bind"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends Property<O>> Optional<P> getOptionalProperty(Class<P> property) {
        return Optional.ofNullable((P) properties.get(property));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends RegistrableProperty<O, EmptyPropertyFactory>> void registerProperty(Class<P> property) {
        ImplementationProperty<RegistrableProperty<O, ?>> implementation = propertiesHandler.getProperty(owner, property);
        properties.put(property, implementation);

        ((RegistrableProperty<O, EmptyPropertyFactory>) implementation).onRegister(uuid, new EmptyPropertyFactory());
        implementation.load(uuid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <F extends PropertyFactory, P extends RegistrableProperty<O, F>> void registerProperty(Class<P> property, F factory) {
        ImplementationProperty<RegistrableProperty<O, ?>> implementation = propertiesHandler.getProperty(owner, property);
        properties.put(property, implementation);

        ((RegistrableProperty<O, F>) implementation).onRegister(uuid, factory);
    }

    @SuppressWarnings("unchecked")
    public <F extends PropertyFactory, P extends RegistrableProperty<O, F>> void unregisterProperty(Class<P> property) {
        ImplementationProperty<? extends Property<O>> implementation = properties.remove(property);

        ((RegistrableProperty<O, F>) implementation).onUnregister(uuid);
    }
}
