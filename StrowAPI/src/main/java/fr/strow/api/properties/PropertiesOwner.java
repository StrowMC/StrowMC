package fr.strow.api.properties;

import fr.strow.api.game.AbstractProperty;

import java.util.Optional;

public interface PropertiesOwner {

    <T extends AbstractProperty> T getProperty(Class<T> property);

    <T extends AbstractProperty> Optional<T> getOptionalProperty(Class<T> property);
}
