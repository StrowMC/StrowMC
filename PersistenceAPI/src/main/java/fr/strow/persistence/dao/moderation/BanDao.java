package fr.strow.persistence.dao.moderation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.moderation.BanBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class BanDao extends AbstractDao {

    @Inject
    public BanDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public boolean hasBan(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.BANS, uuid.toString());
        }
    }

    public List<BanBean> loadBans(UUID uuid) {
        List<BanBean> beans;
        final Type type = new TypeToken<List<BanBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            beans = gson.fromJson(jedis.hget(Tables.BANS, uuid.toString()), type);
        }

        return beans;
    }

    public void saveBans(UUID uuid, List<BanBean> beans) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.BANS,uuid.toString(), gson.toJson(beans));
        }
    }
}
