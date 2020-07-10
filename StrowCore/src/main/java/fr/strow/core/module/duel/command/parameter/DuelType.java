package fr.strow.core.module.duel.command.parameter;

import fr.strow.core.module.duel.util.Duel;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelType extends Parameter<Duel.Type> {

    public DuelType() {
        super("dueltype");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return Duel.Type.getByName(o).isPresent();
                    }

                    @Override
                    public String getMessage(String o) {
                        return "§cType introuvable : 1v1, 2v2, 8v8";
                    }
                }
        );
    }

    @Override
    public Duel.Type get(String arg) {
        return Duel.Type.getByName(arg).get();
    }
}
