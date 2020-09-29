package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionDescription;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionDescriptionBean;
import fr.strow.persistence.dao.factions.FactionDescriptionDao;

import java.util.UUID;

public class FactionDescriptionProperty implements FactionDescription, ImplementationProperty<FactionDescription> {

    private final FactionDescriptionDao factionDescriptionDao;

    private UUID factionUuid;
    private String description;

    @Inject
    public FactionDescriptionProperty(FactionDescriptionDao factionDescriptionDao) {
        this.factionDescriptionDao = factionDescriptionDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        this.factionUuid = factionUuid;

        if (factionDescriptionDao.hasFactionDescription(factionUuid)) {
            FactionDescriptionBean bean = factionDescriptionDao.loadFactionDescription(factionUuid);
            description = bean.getDescription();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID factionUuid) {
        FactionDescriptionBean bean = new FactionDescriptionBean(factionUuid, description);
        factionDescriptionDao.saveFactionDescription(bean);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        if (this.description == null || !this.description.equals(description)) {
            this.description = description;

            save(factionUuid);
        }
    }
}
