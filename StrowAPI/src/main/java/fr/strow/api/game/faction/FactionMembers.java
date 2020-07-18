package fr.strow.api.game.faction;

import fr.strow.api.game.Property;

import java.util.List;
import java.util.UUID;

public interface FactionMembers extends Property<Faction> {

    List<UUID> getMembers();

    void addMember(UUID member);

    void removeMember(UUID member);
}
