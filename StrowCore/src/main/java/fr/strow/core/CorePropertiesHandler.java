package fr.strow.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.property.Property;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.api.property.PropertiesOwner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CorePropertiesHandler implements PropertiesHandler {

    private static final Map<Class<? extends PropertiesOwner<?>>, Map<Class<? extends Property<?>>, Class<? extends ImplementationProperty<?>>>> properties = new HashMap<>();

    private final Injector injector;

    @Inject
    public CorePropertiesHandler(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <O extends PropertiesOwner<O>, P extends Property<O>> void bindProperty(Class<O> owner, Class<P> property, Class<? extends ImplementationProperty<P>> implementation) {
        if (!properties.containsKey(owner)) {
            properties.put(owner, new HashMap<>());
        }

        properties.get(owner).put(property, implementation);
    }

    @Override
    public <O extends PropertiesOwner<O>> void unbindProperty(Class<O> owner, Class<? extends Property<O>> property) {
        properties.get(owner).remove(property);
    }

    @Override
    public <O extends PropertiesOwner<O>> void unbindProperties(Class<O> owner) {
        properties.remove(owner);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <O extends PropertiesOwner<O>, P extends Property<O>> ImplementationProperty<P> getProperty(Class<O> owner, Class<? extends P> property) {
        Class<? extends ImplementationProperty<P>> implementation = (Class<? extends ImplementationProperty<P>>) properties.get(owner).get(property);

        if (implementation == null) {
            throw new IllegalArgumentException("Illegal property : Maybe she isn't binded ?");
        } else {
            return injector.getInstance(implementation);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <O extends PropertiesOwner<O>, P extends Property<O>> Map<Class<? extends P>, ImplementationProperty<? extends P>> getProperties(Class<O> owner, UUID uuid) {
        Map<Class<? extends P>, ImplementationProperty<? extends P>> properties = new HashMap<>();

        for (Class<? extends Property<O>> property : getProperties(owner)) {
            ImplementationProperty<? extends P> implementation = (ImplementationProperty<? extends P>) getProperty(owner, property);

            if (implementation.load(uuid)) {
                properties.put((Class<? extends P>) property, implementation);
            }
        }

        return properties;
    }

    @SuppressWarnings("unchecked")
    private <O extends PropertiesOwner<O>> List<Class<? extends Property<O>>> getProperties(Class<O> owner) {
        return CorePropertiesHandler.properties.get(owner)
                .keySet()
                .stream()
                .map(property -> (Class<? extends Property<O>>) property)
                .collect(Collectors.toList());
    }
}
