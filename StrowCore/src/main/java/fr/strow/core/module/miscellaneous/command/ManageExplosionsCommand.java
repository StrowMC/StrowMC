package fr.strow.core.module.miscellaneous.command;

import fr.strow.api.service.MessageService;
import fr.strow.core.module.miscellaneous.command.parameter.EnableParameter;
import fr.strow.core.module.miscellaneous.listener.ExplosionListener;
import fr.strow.core.utils.Utils;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class ManageExplosionsCommand extends EvolvedCommand {

    private final Parameter<Boolean> enable = new EnableParameter();

    private final MessageService messageService;

    public ManageExplosionsCommand(MessageService messageService) {
        super(
                CommandDescription
                        .builder()
                        .withName("explosions")
                        .withDescription("Permet d'activer/désactiver les explosions sur le serveur")
                        .withPermission("*")
                        .build()
        );
        define();
        this.messageService = messageService;
    }

    @Override
    protected void define() {
        addParam(enable, false);
    }

    @Override
    protected void execute(CommandSender sender) {

        Optional<Boolean> enable = readOptionalArg();

        Utils.ifPresentOrElse(
                enable,
                bool -> {
                    ExplosionListener.enabled = bool;
                    if (sender instanceof Player) {
                        messageService.sendMessage(((Player) sender).getUniqueId(), "§aExplosions " + (bool ? "§eactivées" : "§cdésactivées"));
                    } else {
                        sender.sendMessage("§aExplosions " + (bool ? "§eactivées" : "§cdésactivées"));
                    }
                },
                () -> {
                    if (sender instanceof Player) {
                        messageService.sendMessage(((Player) sender).getUniqueId(), "§aLes explosions sont actuellement " + (ExplosionListener.enabled ? "§eactivées" : "§cdésactivées"));
                    } else {
                        sender.sendMessage("§aLes explosions sont actuellement " + (ExplosionListener.enabled ? "§eactivées" : "§cdésactivées"));
                    }
                }
        );
    }
}
