package fr.strow.core.module.duel.command.parameter;

import fr.strow.core.module.duel.command.SetInventoryCommand;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class InventoryType extends Parameter<SetInventoryCommand.InventoryType> {

    public InventoryType() {
        super("inventorytype");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return SetInventoryCommand.InventoryType.getByName(o).isPresent();
                    }

                    @Override
                    public String getMessage(String o) {
                        return "§cType d'inventaire introuvable";
                    }
                }
        );
    }

    @Override
    public SetInventoryCommand.InventoryType get(String arg) {
        return SetInventoryCommand.InventoryType.getByName(arg).get();
    }
}
