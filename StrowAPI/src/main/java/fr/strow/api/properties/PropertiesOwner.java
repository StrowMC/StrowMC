package fr.strow.api.properties;

import java.util.Optional;

public interface PropertiesOwner {

    <T extends Property> T getProperty(Class<T> property);

    <T extends Property> Optional<T> getOptionalProperty(Class<T> property);
}
