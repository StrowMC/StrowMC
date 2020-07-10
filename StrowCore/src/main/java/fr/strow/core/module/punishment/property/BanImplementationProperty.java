package fr.strow.core.module.punishment.property;

import com.google.inject.Inject;
import fr.strow.api.game.moderation.Sanction;
import fr.strow.persistence.beans.moderation.BanBean;
import fr.strow.persistence.dao.moderation.BanDao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanImplementationProperty implements PersistentImplementationProperty, Sanction<BanImplementationProperty.Ban> {

    private final BanDao banDao;

    private final List<BanImplementationProperty.Ban> mutes = new ArrayList<>();

    @Inject
    public BanImplementationProperty(BanDao banDao) {
        this.banDao = banDao;
    }

    @Override
    public boolean load(UUID uuid) {
        if (banDao.hasBan(uuid)) {
            List<BanBean> beans = banDao.loadBans(uuid);

            for (BanBean bean : beans) {
                BanImplementationProperty.Ban mute = new Ban(bean.getReason(), bean.getSanctionerUuid(), bean.getStartingTimestamp(), bean.getEndingTimestamp());
                mutes.add(mute);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID uuid) {
        List<BanBean> beans = new ArrayList<>();

        for (BanImplementationProperty.Ban mute : mutes) {
            BanBean bean = new BanBean(uuid, mute.reason, mute.sanctionerUuid, mute.startingTimestamp, mute.endingTimestamp);
            beans.add(bean);
        }

        banDao.saveBans(uuid, beans);
    }

    @Override
    public void add(BanImplementationProperty.Ban mute) {
        mutes.add(mute);
    }

    @Override
    public List<BanImplementationProperty.Ban> get() {
        return mutes;
    }

    public static class Ban implements fr.strow.api.game.moderation.Ban {

        private final String reason;
        private final UUID sanctionerUuid;
        private final Timestamp startingTimestamp;
        private final Timestamp endingTimestamp;

        public Ban(String reason, UUID sanctionerUuid, Timestamp startingTimestamp, Timestamp endingTimestamp) {
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
