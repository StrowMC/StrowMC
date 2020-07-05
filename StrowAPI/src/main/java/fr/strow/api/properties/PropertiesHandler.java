package fr.strow.api.properties;

import java.util.List;

public interface PropertiesHandler {

    void registerProperty(Class<? extends Property> property);

    List<Property> getProperties();
}
