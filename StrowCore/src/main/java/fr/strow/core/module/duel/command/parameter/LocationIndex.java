package fr.strow.core.module.duel.command.parameter;

import fr.strow.core.utils.Utils;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class LocationIndex extends Parameter<Integer> {

    public LocationIndex() {
        super("locationindex");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Arrays.asList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return Utils.isInteger(o);
                    }

                    @Override
                    public String getMessage(String o) {
                        return "§cVeuiller entrer un nombre";
                    }
                },
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return Integer.parseInt(o) == 1 || Integer.parseInt(o) == 2;
                    }

                    @Override
                    public String getMessage(String o) {
                        return "§cVeuillez entrer un nombre entre 1 et 2";
                    }
                }
        );
    }

    @Override
    public Integer get(String arg) {
        return Integer.parseInt(arg);
    }
}
