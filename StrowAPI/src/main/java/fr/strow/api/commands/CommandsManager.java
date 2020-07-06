/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 19:40
 */

package fr.strow.api.commands;

import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.Requirement;
import me.choukas.commands.api.Parameter;
import me.choukas.commands.utils.Tuple;

import java.util.Map;

public interface CommandsManager {

    void registerCommand(EvolvedCommand command);

    void registerCommand(String name, EvolvedCommand command);

    default void registerCommand(Tuple<String, EvolvedCommand> command) {
        registerCommand(command.getKey(), command.getValue());
    }

    void unregisterCommand(EvolvedCommand command);

    <T extends EvolvedCommand> T getCommand(Class<T> command);

    void registerCondition(Requirement requirement);

    void unregisterCondition(Requirement requirement);

    <T extends Requirement> T getCondition(Class<T> condition);

    void registerParameter(Parameter<?> parameter);

    void unregisterParameter(Parameter<?> parameter);

    <T extends Parameter<?>> T getParameter(Class<T> parameter);

    /**
     * Get the commands list
     *
     * @return list of the commands
     */
    Map<String, EvolvedCommand> getCommands();
}
