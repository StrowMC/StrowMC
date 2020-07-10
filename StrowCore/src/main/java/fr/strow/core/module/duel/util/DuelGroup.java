package fr.strow.core.module.duel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Hokkaydo on 10-07-2020.
 */
public class DuelGroup {

    private final List<UUID> players = new ArrayList<>();
    private final UUID leader;

    public DuelGroup(UUID leader) {
        this.leader = leader;
        addPlayer(leader);
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public UUID getLeader() {
        return leader;
    }

    public void removePlayer(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public void addPlayer(UUID uniqueId) {
        players.add(uniqueId);
    }
}
