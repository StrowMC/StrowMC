package fr.strow.core.module.punishment.command.parameter;

import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class ReasonParameter extends Parameter<String> {

    public ReasonParameter() {
        super("motif");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String s) {
                return true;
            }

            @Override
            public String getMessage(String s) {
                return "§cArgument non reconnu. Veuillez donner la raison sous forme d'un message";
            }
        });
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
