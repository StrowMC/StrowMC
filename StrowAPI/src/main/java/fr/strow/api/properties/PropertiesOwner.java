package fr.strow.api.properties;

import fr.strow.api.game.Property;

import java.util.Optional;

public interface PropertiesOwner {

    <T extends Property> T getProperty(Class<T> property);

    <T extends Property> Optional<T> getOptionalProperty(Class<T> property);
}
