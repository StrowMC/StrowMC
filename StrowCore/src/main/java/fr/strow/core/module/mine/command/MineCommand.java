package fr.strow.core.module.mine.command;

import com.google.inject.Inject;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineCommand extends EvolvedCommand {

    @Inject
    public MineCommand() {
        super(CommandDescription.builder()
                .withName("mine")
                .withPermission("strow.mine")
                .withDescription("Permet de gérer les mines")
                .build());
        define();
    }

    @Override
    protected void define() {
        addSubCommand(new MineCreateCommand());
        addSubCommand(new MineDeleteCommand());
        addSubCommand(new MineResetCommand());
        addSubCommand(new MineSetBlockCommand());
        addSubCommand(new MineSetResetCommand());
    }
}
