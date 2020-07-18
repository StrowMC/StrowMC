package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionHomeBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionHomeDao extends AbstractDao {

    @Inject
    public FactionHomeDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasHome(UUID factionUuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTION_HOMES, factionUuid.toString());
        }
    }

    public FactionHomeBean loadFactionHome(UUID factionUuid) {
        FactionHomeBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            bean = gson.fromJson(jedis.hget(Tables.FACTION_HOMES, factionUuid.toString()), FactionHomeBean.class);
        }

        return bean;
    }

    public void saveFactionHome(FactionHomeBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.FACTION_HOMES, bean.getFactionUuid().toString(), gson.toJson(bean));
        }
    }
}
