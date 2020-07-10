/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:56
 */

package fr.strow.api.game.factions;

import fr.strow.api.game.factions.profile.FactionProfile;
import fr.strow.api.properties.PropertiesOwner;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public interface Faction extends PropertiesOwner {

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

    /**
     * Get the faction points
     *
     * @return points of the faction
     */
    int getPoints();

    /**
     * Add points to the faction
     *
     * @param amount amount to add
     */
    void addPoints(int amount);

    /**
     * Remove points from the faction
     *
     * @param amount amount to remove
     */
    void removePoints(int amount);

    /**
     * Get the faction home
     *
     * @return home of the faction
     */
    Optional<Location> getHome();

    /**
     * Change the faction home
     *
     * @param home new home
     */
    void setHome(Location home);

    /**
     * Get the faction warps
     *
     * @return warps of the faction
     */
    Map<String, Location> getWarps();

    /**
     * Get the faction claims collection
     *
     * @return collection of the faction claims
     */
    List<Chunk> getClaims();

    /**
     * Get the faction members collection
     *
     * @return collection of the faction members
     */
    Collection<UUID> getMembers();

    /**
     * Get faction unique id
     *
     * @return faction {@link UUID}
     * */
    UUID getUniqueId();

    /**
     * Get faction description
     *
     * @return {@link String} containing faction description
     * */
    FactionDescription getFactionDescription();

    /**
     * Get faction leader
     *
     * @return {@link FactionProfile} representing faction leader
     * */
    FactionProfile getLeader();

    /**
     * Change faction leader
     *
     * @param leader : {@link FactionProfile} representing new faction leader
     * */
    void setLeader(FactionProfile leader);

    /**
     * Send a message to all online faction members
     *
     * @param component :  {@link BaseComponent} containing message
     * */
    void sendMessage(BaseComponent component);
}
