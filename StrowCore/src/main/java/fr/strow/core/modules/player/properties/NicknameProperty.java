package fr.strow.core.modules.player.properties;

import com.google.inject.Inject;
import fr.strow.api.game.player.Nickname;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.NicknameBean;
import fr.strow.persistence.dao.NicknameDao;

import java.util.UUID;

public class NicknameProperty implements Nickname, ImplementationProperty<Nickname> {

    private final NicknameDao nicknameDao;

    private UUID uuid;
    private String nickname;

    @Inject
    public NicknameProperty(NicknameDao nicknameDao) {
        this.nicknameDao = nicknameDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        NicknameBean bean = nicknameDao.loadNickname(uuid);
        nickname = bean.getNickname();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        NicknameBean bean = new NicknameBean(uuid, nickname);
        nicknameDao.saveNickname(bean);
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        if (!this.nickname.equals(nickname)) {
            this.nickname = nickname;

            save(uuid);
        }
    }
}
