package fr.strow.api.game.faction;

import fr.strow.api.property.Property;

import java.util.List;
import java.util.UUID;

public interface FactionMembers extends Property<Faction> {

    void addMember(UUID member);

    void removeMember(UUID member);

    boolean containsMember(UUID member);

    List<UUID> getMembers();
}
