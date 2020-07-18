package fr.strow.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.strow.api.game.Property;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.api.property.PropertiesOwner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CorePropertiesHandler implements PropertiesHandler {

    private static final Map<Class<? extends PropertiesOwner<?>>, Map<Class<? extends Property<?>>, Class<? extends ImplementationProperty>>> properties = new HashMap<>();

    private final Injector injector;

    @Inject
    public CorePropertiesHandler(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <T extends PropertiesOwner<T>> void bindProperty(Class<T> owner, Class<? extends Property<T>> property, Class<? extends ImplementationProperty> implementation) {
        if (!properties.containsKey(owner)) {
            properties.put(owner, new HashMap<>());
        }

        properties.get(owner).put(property, implementation);
    }

    @Override
    public <T extends PropertiesOwner<T>> void unbindProperty(Class<T> owner, Class<? extends Property<T>> property) {
        properties.get(owner).remove(property);
    }

    @Override
    public <T extends PropertiesOwner<T>> void unbindProperties(Class<T> owner) {
        properties.remove(owner);
    }


    @Override
    public <T extends PropertiesOwner<T>> ImplementationProperty getProperty(Class<T> owner, Class<? extends Property<T>> property) {
        Class<? extends ImplementationProperty> implementation = properties.get(owner).get(property);

        if (implementation == null) {
            throw new IllegalArgumentException("Illegal property : Maybe she isn't binded ?");
        } else {
            return injector.getInstance(implementation);
        }
    }

    @Override
    public <T extends PropertiesOwner<T>> Map<Class<? extends Property<T>>, ImplementationProperty> getProperties(Class<T> owner, UUID uuid) {
        Map<Class<? extends Property<T>>, ImplementationProperty> properties = new HashMap<>();

        for (Class<? extends Property<T>> property : getProperties(owner)) {
            ImplementationProperty implementation = getProperty(owner, property);

            if (implementation.load(uuid)) {
                properties.put(property, implementation);
            }
        }

        return properties;
    }

    @SuppressWarnings("unchecked")
    private <T extends PropertiesOwner<T>> List<Class<? extends Property<T>>> getProperties(Class<T> owner) {
        return CorePropertiesHandler.properties.get(owner)
                .keySet()
                .stream()
                .map(property -> (Class<? extends Property<T>>) property)
                .collect(Collectors.toList());
    }
}
