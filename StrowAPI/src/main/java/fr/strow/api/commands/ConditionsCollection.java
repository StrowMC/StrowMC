/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 21:23
 */

package fr.strow.api.commands;

import me.choukas.commands.api.Condition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConditionsCollection {

    private static final Map<Class<? extends Condition>, Condition> conditions = new HashMap<>();

    public void registerCondition(Condition condition) {
        conditions.put(condition.getClass(), condition);
    }

    public void unregisterCondition(Condition condition) {
        conditions.remove(condition.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends Condition> T getCondition(Class<T> clazz) {
        return (T) conditions.get(clazz);
    }

    public Collection<Condition> getConditions() {
        return conditions.values();
    }
}
