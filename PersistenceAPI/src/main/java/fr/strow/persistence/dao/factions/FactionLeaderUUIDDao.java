package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionLeaderUUIDBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionLeaderUUIDDao extends AbstractDao {

    @Inject
    public FactionLeaderUUIDDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionLeaderUUIDBean loadFactionLeaderUuid(UUID uuid) {
        FactionLeaderUUIDBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);

            UUID leaderUuid = factionBean.getLeaderUuid();
            bean = new FactionLeaderUUIDBean(uuid, leaderUuid);
        }

        return bean;
    }

    public void saveFactionLeaderUuid(FactionLeaderUUIDBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID uuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, uuid.toString()), FactionBean.class);
            factionBean.setLeaderUuid(bean.getLeaderUuid());

            jedis.hset(Tables.FACTIONS, uuid.toString(), gson.toJson(factionBean));
        }
    }
}
