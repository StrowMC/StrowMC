package fr.strow.core.module.miscelaneous.command.parameter;

import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class EnableParameter extends Parameter<Boolean> {

    public EnableParameter() {
        super("enable");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return o.equalsIgnoreCase("on") || o.equalsIgnoreCase("off");
                    }

                    @Override
                    public String getMessage(String o) {
                        return "§cVeuillez entrer une valeur booléenne : on | off";
                    }
                }
        );
    }

    @Override
    public Boolean get(String arg) {
        return arg.equalsIgnoreCase("on");
    }
}
