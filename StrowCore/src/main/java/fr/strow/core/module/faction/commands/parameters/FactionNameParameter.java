package fr.strow.core.module.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.factions.FactionManager;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FactionNameParameter extends Parameter<String> {

    private final FactionManager factionManager;

    @Inject
    public FactionNameParameter(FactionManager factionManager) {
        super("name");

        this.factionManager = factionManager;
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Arrays.asList(
                new Condition<String>() {
                    private final Pattern PATTERN = Pattern.compile("[a-zA-Z].{2,254}");

                    @Override
                    public boolean check(String arg) {
                        return PATTERN.matcher(arg).matches();
                    }

                    @Override
                    public String getMessage(String arg) {
                        return "Le nom de la faction doit commencer par une lettre et doit comporter aumoins 3 caractères";
                    }
                },
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        return !factionManager.factionExists(arg);
                    }

                    @Override
                    public String getMessage(String arg) {
                        return "Ce nom est déjà pris par une autre faction";
                    }
                }
        );
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
