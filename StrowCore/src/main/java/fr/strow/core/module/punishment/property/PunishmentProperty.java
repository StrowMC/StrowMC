package fr.strow.core.module.punishment.property;

import fr.strow.api.properties.ImplicitInitialisedProperty;
import fr.strow.api.properties.OptionalPersistentProperty;
import fr.strow.core.module.punishment.utils.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class PunishmentProperty implements ImplicitInitialisedProperty, OptionalPersistentProperty {

    private final List<Punishment> punishments = new ArrayList<>();

    @Override
    public void save(UUID uuid) {

    }

    @Override
    public void load(UUID uuid) {

    }

    @Override
    public boolean has(UUID uuid) {
        return false;
    }

    public List<Punishment> getByType(Punishment.Type type) {
        return punishments.stream().filter(p -> p.getType() == type).collect(Collectors.toList());
    }

    public void addPunishment(Punishment punishment) {
        punishments.add(punishment);
    }

    public Optional<Punishment> getMostRecentActivePunishmentByType(Punishment.Type type) {
        List<Punishment> punishments = getByType(type).stream().filter(Punishment::isActive).sorted((o1, o2) -> {
            if (o1.getEnd().before(o2.getEnd())) return -1;
            if (o1.getEnd().equals(o2.getEnd())) return 0;
            return 1;
        }).collect(Collectors.toList());
        if (punishments.size() == 0) return Optional.empty();
        return Optional.of(punishments.get(punishments.size() - 1));
    }
}
