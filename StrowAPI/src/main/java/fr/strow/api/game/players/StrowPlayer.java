/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:44
 */

package fr.strow.api.game.players;

import fr.strow.api.game.OptionalProperty;
import fr.strow.api.game.Property;
import fr.strow.api.properties.AbstractProperty;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Collection;
import java.util.Optional;

public interface StrowPlayer {

    <T extends Property> T get(Class<T> property);

    <T extends OptionalProperty> Optional<T> getOptionalProperty(Class<T> property);

    Collection<AbstractProperty> getProperties();

    void sendMessage(String message);

    void sendMessage(String format, Object... args);

    void sendMessage(BaseComponent component);
}
