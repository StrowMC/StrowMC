/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 21:23
 */

package fr.strow.api.commands;

import me.choukas.commands.api.Parameter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParametersCollection {

    private static final Map<Class<? extends Parameter<?>>, Parameter<?>> parameters = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void registerParameter(Parameter<?> parameter) {
        parameters.put((Class<? extends Parameter<?>>) parameter.getClass(), parameter);
    }

    public void unregisterParameter(Parameter<?> parameter) {
        parameters.remove(parameter.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends Parameter<?>> T getParameter(Class<T> clazz) {
        return (T) parameters.get(clazz);
    }

    public Collection<Parameter<?>> getParameters() {
        return parameters.values();
    }

}
