package fr.strow.core.module.duel.util;

import com.google.inject.Injector;
import fr.strow.api.service.MessageService;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelGroupManager {
    private static final List<DuelGroup> groups = new ArrayList<>();
    public static Injector injector;

    public static void addGroup(DuelGroup duelGroup) {
        groups.add(duelGroup);
    }

    public static Optional<DuelGroup> getPlayerGroup(Player player) {
        return groups.stream().filter(g -> g.getPlayers().contains(player.getUniqueId())).findFirst();
    }

    public static void disbandGroup(DuelGroup duelGroup) {
        groups.remove(duelGroup);
        duelGroup.getPlayers().forEach(uuid -> injector.getInstance(MessageService.class).sendMessage(uuid, "§cVotre groupe a été dissous"));
    }

    public static void leaveGroup(Player player, DuelGroup duelGroup) {
        duelGroup.removePlayer(player.getUniqueId());
        duelGroup.getPlayers().forEach(uuid -> injector.getInstance(MessageService.class).sendMessage(uuid, "§a%s s'est déconnecté et a quitté le groupe", player.getName()));
    }
}
