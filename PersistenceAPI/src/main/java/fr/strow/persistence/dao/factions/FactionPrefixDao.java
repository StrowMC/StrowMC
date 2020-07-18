package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionPrefixBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class FactionPrefixDao extends AbstractDao {

    @Inject
    public FactionPrefixDao(RedisAccess redisAccess, Gson gson) {
        super(redisAccess, gson);
    }

    public FactionPrefixBean loadFactionPrefix(UUID factionUuid) {
        FactionPrefixBean bean;

        try (Jedis jedis = redisAccess.getResource()) {
            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, factionUuid.toString()), FactionBean.class);

            String prefix = factionBean.getPrefix();
            bean = new FactionPrefixBean(factionUuid, prefix);
        }

        return bean;
    }

    public void saveFactionPrefix(FactionPrefixBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            UUID factionUuid = bean.getUuid();

            FactionBean factionBean = gson.fromJson(jedis.hget(Tables.FACTIONS, factionUuid.toString()), FactionBean.class);
            factionBean.setPrefix(bean.getPrefix());

            jedis.hset(Tables.FACTIONS, factionUuid.toString(), gson.toJson(factionBean));
        }
    }
}
