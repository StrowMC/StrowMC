package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionLeaderBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionLeaderDao extends AbstractDao {

    @Inject
    public FactionLeaderDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionLeaderBean loadFactionLeaderUuid(UUID factionUuid) {
        FactionLeaderBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, factionUuid.toString()), FactionBean.class);

            UUID leaderUuid = factionBean.getLeaderUuid();
            bean = new FactionLeaderBean(factionUuid, leaderUuid);
        }

        return bean;
    }

    public void saveFactionLeaderUuid(FactionLeaderBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID factionUuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, factionUuid.toString()), FactionBean.class);
            factionBean.setLeaderUuid(bean.getLeaderUuid());

            jedis.hset(Tables.FACTIONS, factionUuid.toString(), gson.toJson(factionBean));
        }
    }
}
