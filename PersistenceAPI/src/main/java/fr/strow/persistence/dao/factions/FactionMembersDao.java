package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionMemberBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class FactionMembersDao extends AbstractDao {

    @Inject
    public FactionMembersDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public List<FactionMemberBean> loadMembers(UUID factionUuid) {
        Type type = new TypeToken<List<FactionMemberBean>>() {}.getType();
        List<FactionMemberBean> beans;

        try (Jedis jedis = redisAccess.getResource()) {
            beans = gson.fromJson(jedis.hget(Tables.FACTION_MEMBERS, factionUuid.toString()), type);
        }

        return beans;
    }

    public void saveMembers(List<FactionMemberBean> beans) {
        try (Jedis jedis = redisAccess.getResource()) {
            for (FactionMemberBean bean : beans) {
                jedis.hset(Tables.FACTION_MEMBERS, bean.getFactionUuid().toString(), gson.toJson(bean));
            }
        }
    }

    public void deleteMembers(UUID factionUuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hdel(Tables.FACTION_MEMBERS, factionUuid.toString());
        }
    }
}
