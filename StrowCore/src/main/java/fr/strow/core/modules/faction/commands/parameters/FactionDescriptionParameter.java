package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FactionDescriptionParameter extends Parameter<String> {

    private final Pattern PATTERN = Pattern.compile(".{3,254}");

    @Inject
    public FactionDescriptionParameter() {
        super("description", true);
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String arg) {
                return PATTERN.matcher(arg).matches();
            }

            @Override
            public String getMessage(String arg) {
                return "La description de la faction doit comporter au moins de 3 caractères";
            }
        });
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
