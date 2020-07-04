package fr.strow.core.module.punishmentmodule.command.parameter;

import me.choukas.commands.api.Parameter;

import java.util.Optional;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class ReasonParameter extends Parameter<String> {

    public ReasonParameter() {
        super("motif");
    }

    @Override
    public Optional<String> check(String s) {
        return Optional.of(s);
    }

    @Override
    public String getMessage(String s) {
        return "§cArgument non reconnu. Veuillez donner la raison sous forme d'un message";
    }
}
