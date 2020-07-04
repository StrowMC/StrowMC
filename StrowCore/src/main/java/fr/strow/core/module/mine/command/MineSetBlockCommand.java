package fr.strow.core.module.mine.command;

import fr.strow.core.module.mine.MineModule;
import fr.strow.core.module.mine.command.parameter.MineBlocksParameter;
import fr.strow.core.module.mine.command.parameter.MineNameParameter;
import fr.strow.core.module.mine.util.Mine;
import fr.strow.core.utils.Utils;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Optional;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineSetBlockCommand extends EvolvedCommand {

    private static final Parameter<String> NAME = new MineNameParameter();
    private static final Parameter<String> BLOCKS = new MineBlocksParameter();

    public MineSetBlockCommand() {
        super(CommandDescription.builder()
                .withName("create")
                .withPermission("strow.mine")
                .withDescription("Permet de configurer les ressources d'une mine")
                .build());
        define();
    }

    @Override
    protected void define() {
        addParam(NAME, true);
        addParam(BLOCKS, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        String name = readArg();
        String blocks = readArg();
        Optional<Mine> mineOptional = MineModule.getByName(name);
        if(mineOptional.isPresent()){
            Mine m = mineOptional.get();
            String[] blockData = blocks.split("%");
            if(blockData.length != 2){
                sender.sendMessage("§cSyntaxe incorrecte");
                return;
            }
            if(!Utils.isInteger(blockData[0])){
                sender.sendMessage("§cSyntaxe incorrecte");
                return;
            }
            int rate = Integer.parseInt(blockData[0]);
            Material material = Material.getMaterial(blockData[1]);
            if(material == null){
                sender.sendMessage("§cType introuvable");
                return;
            }
            m.addConfiguration(rate, material);
        }else{
            sender.sendMessage("§cMine introuvable");
        }
    }
}
