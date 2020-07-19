package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.services.Messaging;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FactionNameParameter extends Parameter<String> {

    private final FactionManager factionManager;
    private final Messaging messaging;

    @Inject
    public FactionNameParameter(FactionManager factionManager, Messaging messaging) {
        super("name");

        this.factionManager = factionManager;
        this.messaging = messaging;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Arrays.asList(
                new Condition<String>() {
                    private final Pattern PATTERN = Pattern.compile("[a-zA-Z].{2,254}");

                    @Override
                    public boolean check(String arg) {
                        return PATTERN.matcher(arg).matches();
                    }

                    @Override
                    public BaseComponent[] getMessage(String arg) {
                        return messaging.errorMessage("Le nom de la faction doit commencer par une lettre et doit comporter aumoins 3 caractères");
                    }
                },
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        return !factionManager.factionExists(arg);
                    }

                    @Override
                    public BaseComponent[] getMessage(String arg) {
                        return messaging.errorMessage("Ce nom est déjà pris par une autre faction");
                    }
                }
        );
    }

    @Override
    public String get(String arg) {
        return arg;
    }
}
