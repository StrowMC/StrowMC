/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 12:30
 */

package fr.strow.api.modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModulesCollection {

    private static final Map<Class<? extends StrowModule>, StrowModule> modules = new HashMap<>();

    public <T extends StrowModule> void add(Class<T> module, T instance) {
        modules.put(module, instance);
    }

    public void remove(Class<? extends StrowModule> module) {
        modules.remove(module);
    }

    @SuppressWarnings("unchecked")
    public <T extends StrowModule> T getInstance(Class<T> module) {
        return (T) modules.get(module);
    }

    public Collection<Class<? extends StrowModule>> getModules() {
        return modules.keySet();
    }
}
