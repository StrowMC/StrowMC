package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionWarpBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class FactionWarpsDao extends AbstractDao {

    @Inject
    public FactionWarpsDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasWarps(UUID factionUuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTION_WARPS, factionUuid.toString());
        }
    }

    public List<FactionWarpBean> loadFactionWarps(UUID factionUuid) {
        List<FactionWarpBean> beans;
        Type type = new TypeToken<List<FactionWarpBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            beans = gson.fromJson(jedis.hget(Tables.FACTION_WARPS, factionUuid.toString()),type);
        }

        return beans;
    }

    public void saveFactionWarps(List<FactionWarpBean> beans) {
        try (Jedis jedis = redisAccess.getResource()) {
            for (FactionWarpBean bean : beans) {
                jedis.hset(Tables.FACTION_WARPS, bean.getFactionUuid().toString(), gson.toJson(bean));
            }
        }
    }
}
