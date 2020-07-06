package fr.strow.api.properties;

import java.util.Collection;

public interface PropertiesHandler {

    void registerProperty(Class<? extends Property> property);

    <T extends Property> T getProperty(Class<T> property);

    Collection<Property> getProperties();
}
