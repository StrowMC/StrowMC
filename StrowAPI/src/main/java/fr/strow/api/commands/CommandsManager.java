/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 19:40
 */

package fr.strow.api.commands;

import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.utils.Tuple;

import java.util.Map;

public interface CommandsManager {

    void registerCommand(String name, EvolvedCommand command);

    void unregisterCommand(String name);

    default void registerCommand(Tuple<String, EvolvedCommand> command) {
        registerCommand(command.getKey(), command.getValue());
    }

    /**
     * Get the commands list
     *
     * @return list of the commands
     */
    Map<String, EvolvedCommand> getCommands();
}
