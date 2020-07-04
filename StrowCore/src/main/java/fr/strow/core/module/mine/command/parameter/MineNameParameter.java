package fr.strow.core.module.mine.command.parameter;

import me.choukas.commands.api.Parameter;

import java.util.Optional;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineNameParameter extends Parameter<String> {

    public MineNameParameter() {
        super("mine");
    }

    @Override
    public Optional<String> check(String s) {
        return Optional.ofNullable(s);
    }

    @Override
    public String getMessage(String s) {
        return "";
    }
}
