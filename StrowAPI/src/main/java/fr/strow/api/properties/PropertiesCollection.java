/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 12:29
 */

package fr.strow.api.properties;

import java.util.ArrayList;
import java.util.List;

public class PropertiesCollection {

    private static final List<PersistentProperty> properties = new ArrayList<>();

    public void registerProperty(PersistentProperty property) {
        properties.add(property);
    }

    public List<PersistentProperty> getProperties() {
        return properties;
    }
}
