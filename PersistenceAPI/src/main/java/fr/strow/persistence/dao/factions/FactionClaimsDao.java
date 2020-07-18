package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionClaimBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class FactionClaimsDao extends AbstractDao {

    @Inject
    public FactionClaimsDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasClaims(UUID factionUuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTION_CLAIMS, factionUuid.toString());
        }
    }

    public List<FactionClaimBean> loadFactionClaims(UUID factionUuid) {
        List<FactionClaimBean> beans;
        Type type = new TypeToken<List<FactionClaimBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            beans = gson.fromJson(jedis.hget(Tables.FACTION_CLAIMS, factionUuid.toString()),type);
        }

        return beans;
    }

    public void saveFactionClaims(List<FactionClaimBean> beans) {
        try (Jedis jedis = redisAccess.getResource()) {
            for (FactionClaimBean bean : beans) {
                jedis.hset(Tables.FACTION_CLAIMS, bean.getFactionUuid().toString(), gson.toJson(bean));
            }
        }
    }
}
