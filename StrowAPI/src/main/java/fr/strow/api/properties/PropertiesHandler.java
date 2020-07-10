package fr.strow.api.properties;

import java.util.Collection;

public interface PropertiesHandler {

    void registerProperty(Class<? extends ImplementationProperty<?>> property);

    <T extends ImplementationProperty<?>> T getProperty(Class<T> property);

    Collection<ImplementationProperty<?>> getProperties();
}
