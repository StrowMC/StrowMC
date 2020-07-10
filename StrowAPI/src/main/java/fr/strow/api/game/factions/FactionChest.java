package fr.strow.api.game.factions;

import fr.strow.api.game.Property;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface FactionChest extends Property {

    /**
     * Get the faction chest
     *
     * @return chest of the faction
     */
    Optional<ItemStack[]> getChest();

    /**
     * Change the faction chest
     *
     * @param chest new chest
     */
    void setChest(ItemStack[] chest);
}
