package fr.strow.persistence.dao.factions;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.dao.factions.profile.FactionProfileDao;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class FactionDao {

    private final SQLAccess sqlAccess;
    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public FactionDao(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson, FactionMembersDao factionMembersDao, FactionProfileDao factionProfileDao) {
        this.sqlAccess = sqlAccess;
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public boolean factionExists(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTIONS, uuid.toString());
        }
    }

    public UUID getUUIDFromName(String name) {
        try (Jedis jedis = redisAccess.getResource()) {
            Optional<FactionBean> optionalFaction = jedis.hgetAll(Tables.FACTIONS)
                    .values()
                    .stream()
                    .map(faction ->
                            gson.fromJson(faction, FactionBean.class))
                    .filter(faction ->
                            faction.getName().equals(name))
                    .findAny();

            if (optionalFaction.isPresent()) {
                return optionalFaction.get().getUuid();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void createFaction(FactionBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.FACTIONS, bean.getUuid().toString(), gson.toJson(bean));
        }
    }

    public void deleteFaction(UUID uuid) {
        // Delete in redis
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hdel(Tables.FACTIONS, uuid.toString());
        }

        // Delete in sql
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM " + Tables.FACTIONS + " WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
