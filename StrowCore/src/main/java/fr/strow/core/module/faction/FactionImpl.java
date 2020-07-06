package fr.strow.core.module.faction;

import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.factions.FactionDescription;
import fr.strow.api.game.factions.profile.FactionProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FactionImpl implements Faction {

    private final UUID uuid;
    private final String name;
    private final FactionDescription description;

    private FactionProfile leader;
    private ItemStack[] chest;
    private int points;
    private Location home;

    private Map<String, Location> warps;
    private List<Chunk> claims;
    private Collection<UUID> members;

    public FactionImpl(UUID uuid,
                       String name,
                       String description,
                       FactionProfile leader) {
        this.uuid = uuid;
        this.description = new FactionDescription() {
            private String description;
            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public void setDescription(String description) {
                this.description = description;
            }
        };
        this.leader = leader;
        this.name = name;
    }

    public FactionImpl(UUID uuid,
                       FactionDescription description,
                       FactionProfile leader,
                       ItemStack[] chest,
                       int points,
                       Location home,
                       Map<String, Location> warps,
                       List<Chunk> claims,
                       List<FactionProfile> members) {
        this.uuid = uuid;
        this.description = description;

        this.leader = leader;
        this.chest = chest;
        this.points = points;
        this.home = home;

        this.warps = warps;
        this.claims = claims;
        this.members = new ArrayList<>(); //TODO
        //this.members = members;
        this.name = ""; //TODO
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void sendMessage(BaseComponent component) {
        for (UUID member : members) {
            Bukkit.getPlayer(member).spigot().sendMessage(component);
        }
    }

    @Override
    public FactionDescription getFactionDescription() {
        return description;
    }

    @Override
    public FactionProfile getLeader() {
        return leader;
    }

    @Override
    public void setLeader(FactionProfile leader) {
        if (!this.leader.equals(leader)) {
            this.leader = leader;
        }
    }

    @Override
    public Optional<ItemStack[]> getChest() {
        return Optional.ofNullable(chest);
    }

    @Override
    public void setChest(ItemStack[] chest) {
        if (!Arrays.equals(this.chest, chest)) {
            this.chest = chest;
        }
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void addPoints(int amount) {
        if (amount > 0) {
            this.points += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removePoints(int amount) {
        if (amount > 0) {
            this.points -= amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Optional<Location> getHome() {
        return Optional.ofNullable(home);
    }

    @Override
    public void setHome(Location home) {
        if (!this.home.equals(home)) {
            this.home = home;
        }
    }

    @Override
    public Map<String, Location> getWarps() {
        return warps;
    }

    @Override
    public List<Chunk> getClaims() {
        return claims;
    }

    @Override
    public List<UUID> getMembers() {
        return new ArrayList<>(members);
    }
}
