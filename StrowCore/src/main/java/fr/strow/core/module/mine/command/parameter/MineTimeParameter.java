package fr.strow.core.module.mine.command.parameter;

import fr.strow.core.utils.Utils;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineTimeParameter extends Parameter<Integer> {

    public MineTimeParameter() {
        super("time");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return Utils.isInteger(o);
                    }

                    @Override
                    public String getMessage(String s) {
                        return "§cArgument invalide. Veuillez entrer un argument sous forme de nombre";
                    }

                }
        );
    }

    @Override
    public Integer get(String arg) {
        return Integer.parseInt(arg);
    }
}
