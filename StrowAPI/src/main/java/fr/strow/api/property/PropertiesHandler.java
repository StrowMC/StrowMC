package fr.strow.api.property;

import fr.strow.api.game.Property;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PropertiesHandler {

    <T extends PropertiesOwner<T>> void bindProperty(Class<T> owner, Class<? extends Property<T>> property, Class<? extends ImplementationProperty> implementation);

    <T extends PropertiesOwner<T>> void unbindProperty(Class<T> owner, Class<? extends Property<T>> property);

    <T extends PropertiesOwner<T>> void unbindProperties(Class<T> owner);

    <T extends PropertiesOwner<T>> ImplementationProperty getProperty(Class<T> owner, Class<? extends Property<T>> property);

    <T extends PropertiesOwner<T>> Map<Class<? extends Property<T>>, ImplementationProperty> getProperties(Class<T> owner, UUID uuid);

   // <T extends PropertiesOwner<T>> List<Class<? extends Property<T>>> getProperties(Class<T> owner);
}
