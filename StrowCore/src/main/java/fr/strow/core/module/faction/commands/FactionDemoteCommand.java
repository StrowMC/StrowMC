/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:01
 */

package fr.strow.core.module.faction.commands;

import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;

public class FactionDemoteCommand extends EvolvedCommand {

    public FactionDemoteCommand() {
        super(CommandDescription.builder()
                .withName("demote")
                .withDescription("")
                .build());
    }
}
