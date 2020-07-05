package fr.strow.core.module.spawner.command.parameter;

import fr.strow.core.module.spawner.Spawner;
import me.choukas.commands.api.Parameter;

import java.util.Optional;

/**
 * Created by Hokkaydo on 03-07-2020.
 */
public class SpawnerParameter extends Parameter<Spawner> {

    public SpawnerParameter() {
        super("spawner");
    }

    @Override
    public Optional<Spawner> check(String arg) {
        return Optional.empty();
    }

    @Override
    public String getMessage(String arg) {
        return null;
    }
}
