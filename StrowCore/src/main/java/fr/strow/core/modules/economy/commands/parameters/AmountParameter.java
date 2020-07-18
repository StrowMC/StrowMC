package fr.strow.core.modules.economy.commands.parameters;

import com.google.inject.Inject;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

public class AmountParameter extends Parameter<Integer> {

    private static final int MAX_AMOUNT = 10000;

    @Inject
    public AmountParameter() {
        super("montant");
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String o) {
                try {
                    int n = Integer.parseInt(o);

                    return n > 0 && n <= MAX_AMOUNT;
                } catch (InputMismatchException e) {
                    return false;
                }
            }

            @Override
            public String getMessage(String o) {
                return "Vous devez saisir un nombre strictement positif et inférieur ou égal à " + MAX_AMOUNT;
            }
        });
    }

    @Override
    public Integer get(String arg) {
        return Integer.parseInt(arg);
    }
}
