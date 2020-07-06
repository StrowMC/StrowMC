/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 21:23
 */

package fr.strow.api.commands;

import me.choukas.commands.api.Requirement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConditionsCollection {

    private static final Map<Class<? extends Requirement>, Requirement> conditions = new HashMap<>();

    public void registerCondition(Requirement requirement) {
        conditions.put(requirement.getClass(), requirement);
    }

    public void unregisterCondition(Requirement requirement) {
        conditions.remove(requirement.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends Requirement> T getCondition(Class<T> clazz) {
        return (T) conditions.get(clazz);
    }

    public Collection<Requirement> getConditions() {
        return conditions.values();
    }
}
