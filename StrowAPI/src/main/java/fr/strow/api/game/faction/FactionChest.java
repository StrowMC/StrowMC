package fr.strow.api.game.faction;

import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.RegistrableProperty;
import org.bukkit.inventory.ItemStack;

public interface FactionChest extends RegistrableProperty<Faction, EmptyPropertyFactory> {

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
