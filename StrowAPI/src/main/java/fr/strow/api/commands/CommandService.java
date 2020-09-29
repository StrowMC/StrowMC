/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 19:40
 */

package fr.strow.api.commands;

import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.Parameter;
import me.choukas.commands.api.Requirement;
import org.bukkit.command.Command;

import java.util.Map;

public interface CommandService {

    void registerCommand(Class<? extends EvolvedCommand> command);

    void unregisterCommand(Class<? extends EvolvedCommand> command);

    void unregisterCommand(String name);

    <T extends EvolvedCommand> T getCommand(Class<T> command);

    <T extends Requirement> T getRequirement(Class<T> condition);

    <T extends Parameter<?>> T getParameter(Class<T> parameter);

    /**
     * Get the commands list
     *
     * @return list of the commands
     */
    Map<String, EvolvedCommand> getCommands();
}
