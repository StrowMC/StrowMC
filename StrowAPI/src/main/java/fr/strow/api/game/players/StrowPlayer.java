/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:44
 */

package fr.strow.api.game.players;

import fr.strow.api.game.AbstractProperty;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.Property;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Collection;
import java.util.Optional;

public interface StrowPlayer {

    <T extends Property> void register(T property);

    <T extends Property> void unregister(Class<T> property);

    <T extends AbstractProperty> T get(Class<T> property);

    <T extends AbstractProperty> Optional<T> getOptionalProperty(Class<T> property);

    Collection<Property> getProperties();

    void sendMessage(String message);

    void sendMessage(String format, Object... args);

    void sendMessage(BaseComponent component);
}
