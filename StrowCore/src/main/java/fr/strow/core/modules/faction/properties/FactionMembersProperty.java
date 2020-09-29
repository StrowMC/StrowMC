package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionMembers;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionMemberBean;
import fr.strow.persistence.dao.factions.FactionMembersDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionMembersProperty implements FactionMembers, ImplementationProperty<FactionMembers> {

    private final FactionMembersDao factionMembersDao;

    List<UUID> members;

    @Inject
    public FactionMembersProperty(FactionMembersDao factionMembersDao) {
        this.factionMembersDao = factionMembersDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        members = new ArrayList<>();
        List<FactionMemberBean> beans = factionMembersDao.loadMembers(factionUuid);

        for (FactionMemberBean bean : beans) {
            members.add(bean.getUuid());
        }

        return true;
    }

    /*@Override
    public void save(UUID factionUuid) {
        List<FactionMemberBean> beans = new ArrayList<>();

        for (UUID member : members) {
            FactionMemberBean bean = new FactionMemberBean(member, factionUuid);
            beans.add(bean);
        }

        factionMembersDao.saveMembers(beans);
    }*/

    @Override
    public void addMember(UUID member) {
        if (!members.contains(member)) {
            members.add(member);
        }
    }

    @Override
    public void removeMember(UUID member) {
        members.remove(member);
    }

    @Override
    public boolean containsMember(UUID member) {
        return false;
    }

    @Override
    public List<UUID> getMembers() {
        return members;
    }
}
