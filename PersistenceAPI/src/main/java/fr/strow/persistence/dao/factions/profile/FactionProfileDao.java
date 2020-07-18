package fr.strow.persistence.dao.factions.profile;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.dao.AbstractDao;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class FactionProfileDao {

    private final SQLAccess sqlAccess;
    private final RedisAccess redisAccess;
    private final Gson gson;

    @Inject
    public FactionProfileDao(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        this.sqlAccess = sqlAccess;
        this.redisAccess = redisAccess;
        this.gson = gson;
    }

    public boolean hasProfile(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            return jedis.hexists(Tables.FACTION_PROFILES, uuid.toString());
        }
    }

    public void createProfile(FactionProfileBean bean) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hset(Tables.FACTION_PROFILES, bean.getUuid().toString(), gson.toJson(bean));
        }
    }

    public void deleteProfile(UUID uuid) {
        try (Jedis jedis = redisAccess.getResource()) {
            jedis.hdel(Tables.FACTION_PROFILES, uuid.toString());
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM " + Tables.FACTION_PROFILES + " WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
