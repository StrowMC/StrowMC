package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionChestBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionChestDao extends AbstractDao {

    @Inject
    public FactionChestDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasChest(UUID factionUuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTION_CHESTS, factionUuid.toString());
        }
    }

    public FactionChestBean loadFactionChest(UUID factionUuid) {
        FactionChestBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            bean = gson.fromJson(jedis.hget(Tables.FACTION_CHESTS, factionUuid.toString()), FactionChestBean.class);
        }

        return bean;
    }

    public void saveFactionChest(FactionChestBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.FACTION_CHESTS, bean.getFactionUuid().toString(), gson.toJson(bean));
        }
    }
}
