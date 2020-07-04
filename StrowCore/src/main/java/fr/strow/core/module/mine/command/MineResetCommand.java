package fr.strow.core.module.mine.command;

import fr.strow.core.module.mine.MineModule;
import fr.strow.core.module.mine.command.parameter.MineNameParameter;
import fr.strow.core.module.mine.util.Mine;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.Optional;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineResetCommand extends EvolvedCommand {

    private static final Parameter<String> NAME = new MineNameParameter();

    public MineResetCommand() {
        super(CommandDescription.builder()
                .withName("create")
                .withPermission("strow.mine")
                .withDescription("Permet de réinitialiser une mine")
                .build());
        define();
    }

    @Override
    protected void define() {
        addParam(NAME, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        String name = readArg();
        Optional<Mine> mineOptional = MineModule.getByName(name);
        if(mineOptional.isPresent()){
            mineOptional.get().reset();
            sender.sendMessage("§aMine réinitialisée avec succès");
        }else{
            sender.sendMessage("§cMine introuvable");
        }
    }
}
