package fr.strow.persistence.dao.factions.profile;

import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionProfileDao {

    private final RedisAccess redisAccess;

    @Inject
    public FactionProfileDao(RedisAccess redisAccess) {
        this.redisAccess = redisAccess;
    }

    public boolean hasProfile(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTION_PROFILES, uuid.toString());
        }
    }
}
