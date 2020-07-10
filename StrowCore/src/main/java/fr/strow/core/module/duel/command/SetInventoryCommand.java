package fr.strow.core.module.duel.command;

import com.google.inject.Inject;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.duel.DuelModule;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class SetInventoryCommand extends EvolvedCommand {

    private static final Parameter<InventoryType> inventoryType = new fr.strow.core.module.duel.command.parameter.InventoryType();

    private final MessageService messageService;

    @Inject
    public SetInventoryCommand(MessageService messageService) {
        super(
                CommandDescription
                        .builder()
                        .withName("setinventory")
                        .withPermission("duel.admin")
                        .withDescription("Permet de définir un inventaire de combat")
                        .build()
        );
        this.messageService = messageService;
        define();
    }

    @Override
    protected void define() {
        addParam(inventoryType, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cVeuillez exécuter cette action en jeu");
            return;
        }

        InventoryType inventoryType = readArg();
        DuelModule.inventories.put(inventoryType, Arrays.asList(((Player) sender).getInventory().getContents(), ((Player) sender).getInventory().getArmorContents()));
        messageService.sendMessage(((Player) sender).getUniqueId(), "§aInventaire défini pour le mode %s", inventoryType.name);
    }

    public enum InventoryType {
        PRACTICE("Practice"),
        GAMBLING("Gambling"),
        FACTION("Faction");

        private final String name;

        InventoryType(String name) {
            this.name = name;
        }

        public static Optional<InventoryType> getByName(String name) {
            return Arrays.stream(values()).filter(i -> i.name.equalsIgnoreCase(name)).findFirst();
        }

        public String getName() {
            return name;
        }
    }
}
