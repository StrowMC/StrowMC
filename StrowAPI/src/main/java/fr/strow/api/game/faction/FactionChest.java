package fr.strow.api.game.faction;

import fr.strow.api.game.Property;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface FactionChest extends Property<Faction> {

    /**
     * Get the faction chest
     *
     * @return chest of the faction
     */
    ItemStack[] getChest();

    /**
     * Change the faction chest
     *
     * @param chest new chest
     */
    void setChest(ItemStack[] chest);
}
