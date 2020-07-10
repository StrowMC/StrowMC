package fr.strow.core.module.duel.util;

import fr.strow.core.module.duel.command.SetInventoryCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelManager {

    private static final Map<Duel.Type, Duel> duels = new HashMap<>();

    public static boolean isInDuel(Player player) {
        return getDuel(player).isPresent();
    }

    public static boolean isInDuel(DuelGroup duelGroup) {
        return getDuel(duelGroup).isPresent();
    }

    public static boolean setRequestingDuelGroup(Duel.Type type, DuelGroup duelGroup, SetInventoryCommand.InventoryType inventoryType) {
        if (duels.containsKey(type)) return false;
        duels.put(type, new Duel(duelGroup, type, inventoryType));
        return true;
    }

    public static void removeDuel(Duel.Type type) {
        duels.remove(type);
    }

    public static void removeDuel(Duel duel) {
        Duel.Type key = null;
        for (Map.Entry<Duel.Type, Duel> entry : duels.entrySet()) {
            if (entry.getValue().equals(duel)) {
                key = entry.getKey();
                break;
            }
        }
        if (key != null) duels.remove(key);
    }

    public static void removeDuel(DuelGroup duelGroup) {
        Duel.Type key = null;
        for (Map.Entry<Duel.Type, Duel> entry : duels.entrySet()) {
            if (entry.getValue().getDuelGroup1().equals(duelGroup) || entry.getValue().getDuelGroup2().equals(duelGroup)) {
                key = entry.getKey();
                break;
            }
        }
        if (key != null) duels.remove(key);
    }

    public static Optional<Duel> getDuel(Duel.Type type) {
        return Optional.ofNullable(duels.get(type));
    }

    public static Optional<Duel> getDuel(Player player) {
        for (Map.Entry<Duel.Type, Duel> entry : duels.entrySet()) {
            if (entry.getValue().getDuelGroup1().getPlayers().contains(player.getUniqueId()))
                return Optional.of(entry.getValue());
            if (entry.getValue().getDuelGroup2().getPlayers().contains(player.getUniqueId()))
                return Optional.of(entry.getValue());
        }
        return Optional.empty();
    }

    public static Optional<Duel> getDuel(DuelGroup duelGroup) {
        for (Map.Entry<Duel.Type, Duel> entry : duels.entrySet()) {
            if (entry.getValue().getDuelGroup1().equals(duelGroup) || entry.getValue().getDuelGroup2().equals(duelGroup)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
