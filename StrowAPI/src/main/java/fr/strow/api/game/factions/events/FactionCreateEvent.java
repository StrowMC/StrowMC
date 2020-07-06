package fr.strow.api.game.factions.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class FactionCreateEvent extends Event implements Cancellable {

    private final UUID player;
    private boolean cancelled;

    /**
     * FactionCreateEvent constructor
     *
     * @param player player who created the faction
     */
    public FactionCreateEvent(UUID player) {
        this.player = player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)  {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    /**
     * Get the player who created the faction
     *
     * @return player who created the faction
     */
    public UUID getPlayer() {
        return player;
    }
}
