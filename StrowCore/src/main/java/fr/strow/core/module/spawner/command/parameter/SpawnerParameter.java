package fr.strow.core.module.spawner.command.parameter;

import fr.strow.core.module.spawner.Spawner;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerParameter extends Parameter<Spawner> {

    public SpawnerParameter() {
        super("spawner");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String o) {
                        return Arrays.stream(Spawner.values()).anyMatch(s -> s.getName().equalsIgnoreCase(o));
                    }

                    @Override
                    public String getMessage(String o) {
                        return "§cSpawner introuvable";
                    }
                }
        );
    }

    /**
     * We are sure the value exist thanks to above {@link Condition#check} method
     */
    @Override
    public Spawner get(String arg) {
        return Arrays.stream(Spawner.values()).filter(s -> s.getName().equalsIgnoreCase(arg)).findFirst().get();
    }
}
