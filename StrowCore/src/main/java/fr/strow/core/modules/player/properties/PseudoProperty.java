package fr.strow.core.modules.player.properties;

import com.google.inject.Inject;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.PseudoBean;
import fr.strow.persistence.dao.PseudoDao;

import java.util.UUID;

public class PseudoProperty extends ImplementationProperty implements Pseudo {

    private final PseudoDao pseudoDao;

    private String pseudo;

    @Inject
    public PseudoProperty(PseudoDao pseudoDao) {
        this.pseudoDao = pseudoDao;
    }

    @Override
    public boolean load(UUID uuid) {
        PseudoBean bean = pseudoDao.loadPseudo(uuid);

        pseudo = bean.getPseudo();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        PseudoBean bean = new PseudoBean(uuid, pseudo);

        pseudoDao.savePseudo(bean);
    }

    @Override
    public String getPseudo() {
        return pseudo;
    }

    @Override
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
