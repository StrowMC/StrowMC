package fr.strow.api.property;

import java.util.Map;
import java.util.UUID;

public interface PropertiesHandler {

    <O extends PropertiesOwner<O>, P extends Property<O>> void bindProperty(Class<O> owner, Class<P> property, Class<? extends ImplementationProperty<P>> implementation);

    <O extends PropertiesOwner<O>> void unbindProperty(Class<O> owner, Class<? extends Property<O>> property);

    <O extends PropertiesOwner<O>> void unbindProperties(Class<O> owner);

    <O extends PropertiesOwner<O>, P extends Property<O>> ImplementationProperty<P> getProperty(Class<O> owner, Class<? extends P> property);

    <O extends PropertiesOwner<O>, P extends Property<O>> Map<Class<? extends P>, ImplementationProperty<? extends P>> getProperties(Class<O> owner, UUID uuid);

   // <O extends PropertiesOwner<O>> List<Class<? extends Property<O>>> getProperties(Class<O> owner);
}
