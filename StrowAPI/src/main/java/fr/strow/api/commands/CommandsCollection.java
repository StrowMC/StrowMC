/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 21:23
 */

package fr.strow.api.commands;

import me.choukas.commands.EvolvedCommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsCollection {

    private static final Map<Class<? extends EvolvedCommand>, EvolvedCommand> commands = new HashMap<>();

    public void registerCommand(EvolvedCommand command) {
        commands.put(command.getClass(), command);
    }

    public void unregisterCommand(EvolvedCommand command) {
        commands.remove(command.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends EvolvedCommand> T getCommand(Class<T> clazz) {
        return (T) commands.get(clazz);
    }

    public Collection<EvolvedCommand> getCommands() {
        return commands.values();
    }
}
