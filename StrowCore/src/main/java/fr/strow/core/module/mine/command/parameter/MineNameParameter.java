package fr.strow.core.module.mine.command.parameter;

import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.List;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineNameParameter extends Parameter<String> {

    public MineNameParameter() {
        super("mine");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return null;
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
