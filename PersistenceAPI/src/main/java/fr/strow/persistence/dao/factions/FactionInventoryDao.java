package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;

public class FactionInventoryDao extends AbstractDao {

    @Inject
    public FactionInventoryDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }
}
