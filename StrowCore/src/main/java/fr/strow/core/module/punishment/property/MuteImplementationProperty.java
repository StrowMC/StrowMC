package fr.strow.core.module.punishment.property;

import com.google.inject.Inject;
import fr.strow.api.game.moderation.Sanction;
import fr.strow.persistence.beans.moderation.MuteBean;
import fr.strow.persistence.dao.moderation.MuteDao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MuteImplementationProperty implements PersistentImplementationProperty, Sanction<MuteImplementationProperty.Mute> {

    private final MuteDao muteDao;

    private final List<Mute> mutes = new ArrayList<>();

    @Inject
    public MuteImplementationProperty(MuteDao muteDao) {
        this.muteDao = muteDao;
    }

    @Override
    public boolean load(UUID uuid) {
        if (muteDao.hasMute(uuid)) {
            List<MuteBean> beans = muteDao.loadMutes(uuid);

            for (MuteBean bean : beans) {
                Mute mute = new Mute(bean.getReason(), bean.getSanctionerUuid(), bean.getStartingTimestamp(), bean.getEndingTimestamp());

                mutes.add(mute);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID uuid) {
        List<MuteBean> beans = new ArrayList<>();

        for (Mute mute : mutes) {
            MuteBean bean = new MuteBean(uuid, mute.reason, mute.sanctionerUuid, mute.startingTimestamp, mute.endingTimestamp);
            beans.add(bean);
        }

        muteDao.saveMutes(uuid, beans);
    }

    @Override
    public void add(Mute mute) {
        mutes.add(mute);
    }

    @Override
    public List<Mute> get() {
        return mutes;
    }

    public static class Mute implements fr.strow.api.game.moderation.Mute {

        private final String reason;
        private final UUID sanctionerUuid;
        private final Timestamp startingTimestamp;
        private final Timestamp endingTimestamp;

        public Mute(String reason, UUID sanctionerUuid, Timestamp startingTimestamp, Timestamp endingTimestamp) {
            this.reason = reason;
            this.sanctionerUuid = sanctionerUuid;
            this.startingTimestamp = startingTimestamp;
            this.endingTimestamp = endingTimestamp;
        }

        @Override
        public String getReason() {
            return reason;
        }

        @Override
        public UUID getSanctionerUuid() {
            return sanctionerUuid;
        }

        @Override
        public Timestamp getStartingTimestamp() {
            return startingTimestamp;
        }

        @Override
        public Timestamp getEndingTimestamp() {
            return endingTimestamp;
        }
    }
}
