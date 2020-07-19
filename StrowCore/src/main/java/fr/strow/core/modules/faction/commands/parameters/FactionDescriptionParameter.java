package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.services.Messaging;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FactionDescriptionParameter extends Parameter<String> {

    private static final Pattern PATTERN = Pattern.compile(".{3,254}");

    private final Messaging messaging;

    @Inject
    public FactionDescriptionParameter(Messaging messaging) {
        super("description", true);
        this.messaging = messaging;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String arg) {
                return PATTERN.matcher(arg).matches();
            }

            @Override
            public BaseComponent getMessage(String arg) {
                return messaging.errorMessage("La description de la faction doit comporter au moins de 3 caractères");
            }
        });
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
